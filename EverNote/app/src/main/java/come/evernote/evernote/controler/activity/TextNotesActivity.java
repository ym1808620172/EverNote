package come.evernote.evernote.controler.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.PhotoBean;

/**
 * Created by dllo on 16/10/17.
 * 文字笔记界面
 *
 * @author 杜显东
 */
public class TextNotesActivity extends AbsBaseActivity {
    private ImageView windowIV;//圈I

    @Override
    protected int setLayout() {
        return R.layout.activity_notes_text;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        if (intent!=null){
            PhotoBean bean = (PhotoBean) intent.getSerializableExtra("photo");
            Log.d("TextNotesActivity", "bean:" + bean);
            Log.d("photo", "bean.getBitmap():" + getBitmap(bean.getBitmap()));
            Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap getBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
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

    }

}
