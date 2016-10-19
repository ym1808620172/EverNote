package come.evernote.evernote.controler.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import come.evernote.evernote.R;

public class SettingActivity extends AbsBaseActivity {

    private LinearLayout head_layout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CoordinatorLayout root_layout;
    private AppBarLayout app_bar_layout;
    private Toolbar mToolbar;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        app_bar_layout = byView(R.id.app_bar_layout);
        mToolbar = byView(R.id.toolbar);
        head_layout = byView(R.id.head_layout);
        root_layout = byView(R.id.root_layout);
        mCollapsingToolbarLayout = byView(R.id.collapsing_toolbar_layout);
    }

    @Override
    protected void initDatas() {
        setIfTitles(1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle("设置");
                } else {
                    mCollapsingToolbarLayout.setTitle(" ");
                }
            }
        });

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
