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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.fragment.TextNotesAboutFragment;
import come.evernote.evernote.controler.fragment.TextNotesMenuFragment;

/**
 * Created by dllo on 16/10/17.
 * 文字笔记界面
 *
 * @author 杜显东
 */
public class TextNotesActivity extends AbsBaseActivity {
    private ImageView windowIV;//圈I
    private ImageView aboutTv;//圈I
    private ImageView menuIv;//菜单(三个点)
    private DrawerLayout drawerLayout;//根布局
    private LinearLayout layout;//抽屉的布局
    private FragmentManager manager;
   private FragmentTransaction transaction;

    @Override
    protected int setLayout() {
        return R.layout.activity_notes_text;
    }

    @Override
    protected void initView() {
        aboutTv = byView(R.id.notes_text_about_img);
        menuIv = byView(R.id.item_text_notes_menu_img);
        drawerLayout = byView(R.id.drawer_layout);
        layout = byView(R.id.drawer_liner_layout);
        menuIv.setOnClickListener(this);
        aboutTv.setOnClickListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//抽屉关闭手势滑动

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
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()){
            case R.id.item_text_notes_menu_img:
                transaction.replace(R.id.drawer_frame_layout,new TextNotesMenuFragment()).commit();
                drawerLayout.openDrawer(layout);
                break;
            case R.id.notes_text_about_img:
                transaction.replace(R.id.drawer_frame_layout,new TextNotesAboutFragment()).commit();
                drawerLayout.openDrawer(layout);
                break;

        }

    }

}
