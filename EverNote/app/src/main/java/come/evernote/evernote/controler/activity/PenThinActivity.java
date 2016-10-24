package come.evernote.evernote.controler.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.PhotoBean;
import come.evernote.evernote.view.PaintView;

/**
 * Created by dllo on 16/10/21.
 * 手写界面
 */
public class PenThinActivity extends AbsBaseActivity {
    private PaintView mPaintView;
    private LinearLayout linearLayout;
    private Button mBtnOk,mBtnClear,mBtnCancel;
    @Override
    protected int setLayout() {
        return R.layout.activity_thin_pen;
    }

    @Override
    protected void initView() {
        mBtnOk = (Button) findViewById(R.id.write_pad_ok);
        mBtnClear = byView(R.id.write_pad_clear);
        mBtnCancel = byView(R.id.write_pad_cancel);
        linearLayout = byView(R.id.thin_pen_layout);
        mBtnOk.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        setIfTitles(1);//去除标题栏
        // 获取屏幕尺寸
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int screenWidth = mDisplayMetrics.widthPixels;
        int screenHeight = mDisplayMetrics.heightPixels;
        mPaintView = new PaintView(this, screenWidth, screenHeight);
        linearLayout.addView(mPaintView);
        mPaintView.requestFocus();

    }

    @Override
    protected void onClickDrawer() {

    }

    @Override
    protected void onClickRight() {

    }

    @Override
    protected void onClickMid() {

    }

    @Override
    protected void onClickLeft() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.write_pad_ok:
                if (mPaintView.getPath().isEmpty()){
                    Toast.makeText(this, "请写下大名", Toast.LENGTH_SHORT).show();

                }else {
                Bitmap bitmap = mPaintView.getPaintBitmap();
                    Bundle bundle = new Bundle();
                    PhotoBean bean = new PhotoBean();
                    bean.setBitmap(getBytes(bitmap));
                    bundle.putSerializable("img",bean);
                    goTo(PenThinActivity.this,TextNotesActivity.class,bundle);
                }
                break;
            case R.id.write_pad_clear:
                mPaintView.clear();
                break;
            case R.id.write_pad_cancel:
                break;
        }

    }

    /**
     * 图片转换为流
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
