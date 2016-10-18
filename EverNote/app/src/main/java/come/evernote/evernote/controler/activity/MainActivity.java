package come.evernote.evernote.controler.activity;


import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import come.evernote.evernote.R;

public class MainActivity extends AbsBaseActivity implements View.OnClickListener {
    private ImageView imageView;//菜单图片
    private FragmentManager manager;//管理者
    private FragmentTransaction transaction;

    private DrawerLayout rootView;// 整个页面的布局对象
    private LinearLayout layout;// 抽屉布局对象
    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        imageView = byView(R.id.main_img);
        imageView.setOnClickListener(this);
        rootView = byView(R.id.root_view);
        layout  = byView(R.id.drawer_view);
    }

    @Override
    protected void initDatas() {


    }

    @Override
    public void onClick(View view) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.main_img:
                break;
        }
    }
    protected void onClickDrawer() {

        rootView.openDrawer(layout);
    }

    @Override
    protected void onClickRight() {
        Log.d("aaaa", "zhixingle");
    }

    @Override
    protected void onClickMid() {

    }

    @Override
    protected void onClickLeft() {

    }


}
