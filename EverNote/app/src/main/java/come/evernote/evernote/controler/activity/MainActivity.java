package come.evernote.evernote.controler.activity;


import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import come.evernote.evernote.R;

public class MainActivity extends AbsBaseActivity implements View.OnClickListener {
    private ImageView imageView;//菜单图片


    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        imageView = byView(R.id.main_img);
        imageView.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

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
    public void onClick(View v) {
    }
}
