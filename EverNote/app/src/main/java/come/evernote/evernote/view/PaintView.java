package come.evernote.evernote.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 自定义画笔
 */
public class PaintView extends View {
    private Paint mPaint;
    private Path mPath;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private int screenWidth, screenHeight;
    private float currentX, currentY;
    private float x ,y;
    private Paint paint;
    private int mMode = 1;
    private int Pen = 1;
    private int Eraser = 2;
    private static final float TOUCH_TOLERANCE = 4;
    private static List<DrawPath> savePath;// 保存Path路径的集合,用List集合来模拟栈
    private DrawPath dp;//记录Path路劲的对象


    public void setmMode(int mMode) {
        this.mMode = mMode;
    }

    public PaintView(Context context, int screenWidth, int screenHeight) {
        super(context);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 去除锯齿
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);


        paint = new Paint();
        paint.setAlpha(0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(20);
        mPath = new Path();
        mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        savePath = new ArrayList<DrawPath>();

    }
    public List<DrawPath> List(){
        return savePath;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
        super.onDraw(canvas);
    }
    private void touch_start(float x,float y){
        mPath.reset();
        mPath.moveTo(x,y);
        currentX = x;
        currentY = y;
        if (mMode == Pen){
            mCanvas.drawPath(mPath,mPaint);
        }
        if (mMode == Eraser){
            mCanvas.drawPath(mPath,paint);
        }
    }
    private void touch_move(float x, float y) {
        float dx = Math.abs(x - currentX);
        float dy = Math.abs(y - currentY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(currentX, currentY, (x + currentX) / 2, (y + currentY) / 2);
            currentX = x;
            currentY = y;
            if (mMode == Pen) {
                mCanvas.drawPath(mPath, mPaint);
            }
            if (mMode == Eraser) {
                mCanvas.drawPath(mPath, paint);
            }
        }
    }
    private void touch_up() {
        mPath.lineTo(currentX, currentY);
        if (mMode == Pen) {
            mCanvas.drawPath(mPath, mPaint);
        }
        if (mMode == Eraser) {
            mCanvas.drawPath(mPath, paint);
        }
        savePath.add(dp);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();
                dp = new DrawPath();
                dp.path = mPath;
                dp.Tpaint = mPaint;
                touch_start(x,y);
                invalidate();

                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                mPath = null;
                break;
        }

        return true;
    }

    public Bitmap getPaintBitmap() {
        return resizeImage(mBitmap, 320, 480);
    }

    public Path getPath() {
        return mPath;
    }

    // 缩放
    public  Bitmap resizeImage(Bitmap bitmap, int width, int height) {
        int originWidth = bitmap.getWidth();
        int originHeight = bitmap.getHeight();

        float scaleWidth = ((float) width) / originWidth;
        float scaleHeight = ((float) height) / originHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, originWidth,
                originHeight, matrix, true);
        return resizedBitmap;
    }

    //清除画板
    public void clear() {
        if (mCanvas != null) {
            mPath.reset();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            invalidate();
        }
    }
    public void undo() {
        mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        if (savePath != null && savePath.size() > 0) {
            savePath.remove(savePath.size() - 1);

            Iterator<DrawPath> iterator = savePath.iterator();
            while (iterator.hasNext()) {
                DrawPath drawPath = iterator.next();
                mCanvas.drawPath(drawPath.path, drawPath.Tpaint);
            }
            invalidate();
        }
    }
    private class DrawPath{
        public Path path;
        public Paint Tpaint;
    }


}
