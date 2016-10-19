package come.evernote.evernote.controler.activity;


import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.adapter.DrawerAdapter;
import come.evernote.evernote.model.bean.DrawerShowBean;

public class MainActivity extends AbsBaseActivity implements View.OnClickListener {
    private ImageView imageView;//菜单图片
    private FragmentManager manager;//管理者
    private FragmentTransaction transaction;
    private DrawerLayout rootView;// 整个页面的布局对象
    private LinearLayout layout;// 抽屉布局对象
    private List<DrawerShowBean> datas;
    private DrawerAdapter adapter;
    private ListView drawerLv;// 抽屉lv
    private int index;
    private TextView forTv;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        imageView = byView(R.id.main_img);
        imageView.setOnClickListener(this);
        rootView = byView(R.id.root_view);
        layout = byView(R.id.drawer_view);
        drawerLv = byView(R.id.drawer_lv);
        forTv = byView(R.id.for_tv);
    }

    @Override
    protected void initDatas() {

        // 头布局
        getHead();
        adapter = new DrawerAdapter(this);
        datas = new ArrayList<>();
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_all_notes), R.mipmap.note));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_note_book), R.mipmap.bijiben));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_shortcut), R.mipmap.wujiaoxing));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_group_chat), R.mipmap.work));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_waster_page), R.mipmap.laji));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_upgrade), R.mipmap.topgrade));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_explore_note), R.mipmap.tansuo));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_set_Up_the), R.mipmap.shezhi));
        adapter.setDatas(datas);
        drawerLv.setAdapter(adapter);

        drawerLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private Intent intent;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("aaaaa", "position:" + position);

                if (position == 0){
                    Toast.makeText(MainActivity.this, "点击头布局", Toast.LENGTH_SHORT).show();
                }
                    if (position== 1){
                        Toast.makeText(MainActivity.this, "所有笔记", Toast.LENGTH_SHORT).show();
                    }

                if (position == 2) {

                    Toast.makeText(MainActivity.this, "2" , Toast.LENGTH_SHORT).show();

                } else if (position == 3) {
                    Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                } else if (position == 4) {
                    Toast.makeText(MainActivity.this, "4", Toast.LENGTH_SHORT).show();
                } else if (position == 5) {
                    Toast.makeText(MainActivity.this, "5", Toast.LENGTH_SHORT).show();
                } else if (position == 6) {
                    Toast.makeText(MainActivity.this, "6", Toast.LENGTH_SHORT).show();
                } else if (position == 7) {
                    Toast.makeText(MainActivity.this, "7", Toast.LENGTH_SHORT).show();
                } else if (position == 8) {
                    Toast.makeText(MainActivity.this, "8", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
                index = position - 1;
                adapter.setIndex(index);
                adapter.notifyDataSetChanged();

            }
        });

        // 定位跳转
        forTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PositonActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getHead() {

        View view = getLayoutInflater().inflate(R.layout.first_page_drawer_header, null);
        drawerLv.addHeaderView(view);
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
    }

    @Override
    protected void onClickMid() {

    }

    @Override
    protected void onClickLeft() {

    }
}
