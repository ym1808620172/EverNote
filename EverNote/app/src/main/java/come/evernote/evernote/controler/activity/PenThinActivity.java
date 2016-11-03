package come.evernote.evernote.controler.activity;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.PhotoBean;
import come.evernote.evernote.view.PaintView;

/**
 * Created by dllo on 16/10/21.
 * 手写界面
 */
public class PenThinActivity extends AbsBaseActivity implements View.OnClickListener {
    private PaintView mPaintView;
    private LinearLayout linearLayout;
    private ImageView returnIv;//退出
    private ImageView penIv;//笔
    private ImageView eraserIv;//橡皮擦
    private ImageView cutIv;//切口
    private ImageView undoIv;//左撤销
    private ImageView redoIv;//右撤销
    private int Pen = 1;
    private int Eraser = 2;
    private EventBus eventBus;

    @Override
    protected int setLayout() {
        return R.layout.activity_thin_pen;
    }

    @Override
    protected void initView() {
        linearLayout = byView(R.id.thin_pen_layout);
        returnIv = byView(R.id.item_pen_thin_return);
        penIv = byView(R.id.item_pen_thin_pen);
        eraserIv = byView(R.id.item_pen_thin_eraser);
        cutIv = byView(R.id.item_pen_thin_cut);
        undoIv = byView(R.id.item_pen_thin_undo);
        redoIv = byView(R.id.item_pen_thin_redo);
        returnIv.setOnClickListener(this);
        penIv.setOnClickListener(this);
        eraserIv.setOnClickListener(this);
        cutIv.setOnClickListener(this);
        undoIv.setOnClickListener(this);
        redoIv.setOnClickListener(this);


    }

    @Override
    protected void initDatas() {
        setIfTitles(1);//去除标题栏
        // 获取屏幕尺寸
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int screenWidth = mDisplayMetrics.widthPixels;
        int screenHeight = mDisplayMetrics.heightPixels ;
        mPaintView = new PaintView(this, screenWidth, screenHeight);
        linearLayout.addView(mPaintView);
        mPaintView.requestFocus();
        eventBus = EventBus.getDefault();//初始化Eventbus
    }

    @Override
    protected void onClickDrawer() {

    }

    @Override
    protected void onClickRight(View v) {

    }

    @Override
    protected void onClickMid() {

    }

    @Override
    protected void onClickLeft() {

    }

    private boolean isClick = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.item_pen_thin_return:
                if (mPaintView.getPath() != null) {
                    if (mPaintView.getPath().isEmpty()) {

                    } else {
                        Bitmap bitmap = mPaintView.getPaintBitmap();
                        PhotoBean bean = new PhotoBean();
                        bean.setBitmap(getBytes(bitmap));
                        eventBus.post(bean);
                    }
                }
                finish();
                break;
            case R.id.item_pen_thin_pen:
                if (!isClick) {
                    mPaintView.setmMode(Pen);
                    penIv.setSelected(true);
                    isClick = true;
                } else if (isClick) {
                    penIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_pen_thin_eraser:
                if (!isClick) {
                    eraserIv.setSelected(true);
                    isClick = true;
                    mPaintView.setmMode(Eraser);
                } else if (isClick) {
                    eraserIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_pen_thin_cut:
                if (!isClick) {
                    cutIv.setSelected(true);
                    isClick = true;
                } else if (isClick) {
                    cutIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_pen_thin_undo:
//                if (!isClick) {
//                    undoIv.setSelected(true);
                    mPaintView.undo();
//                    isClick = true;
//                } else if (isClick) {
//                    undoIv.setSelected(false);
//                    isClick = false;
//                }
                break;
            case R.id.item_pen_thin_redo:
                if (!isClick) {
                    redoIv.setSelected(true);
                    isClick = true;
                } else if (isClick) {
                    redoIv.setSelected(false);
                    isClick = false;
                }
                break;
        }

    }

    /**
     * 图片转换为流
     *
     * @param bitmap
     * @return
     */
    public static byte[] getBytes(Bitmap bitmap) {
        //实例化字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);//压缩位图
        return baos.toByteArray();//创建分配字节数组
    }


}
