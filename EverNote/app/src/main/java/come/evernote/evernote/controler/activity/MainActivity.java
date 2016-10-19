package come.evernote.evernote.controler.activity;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.DrawerShowBean;

public class MainActivity extends AbsBaseActivity implements View.OnClickListener {
    private ImageView imageView;//菜单图片
    private FragmentManager manager;//管理者
    private FragmentTransaction transaction;
    private DrawerLayout rootView;// 整个页面的布局对象
    private LinearLayout layout;// 抽屉布局对象
    private List<DrawerShowBean> datas;
    private  DrawerAdapter adapter ;
    private ListView drawerLv;
    private  int index;
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
        drawerLv =byView(R.id.drawer_lv);
    }

    @Override
    protected void initDatas() {
        adapter = new DrawerAdapter(this);
        datas = new ArrayList<>();
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_all_notes),R.mipmap.note));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_note_book),R.mipmap.bijiben));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_shortcut),R.mipmap.wujiaoxing));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_group_chat),R.mipmap.work));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_waster_page),R.mipmap.laji));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_upgrade),R.mipmap.topgrade));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_explore_note),R.mipmap.tansuo));
        datas.add(new DrawerShowBean(getResources().getString(R.string.first_page_drawer_set_Up_the),R.mipmap.shezhi));
        adapter.setDatas(datas);
        drawerLv.setAdapter(adapter);

        drawerLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){

                    Toast.makeText(MainActivity.this, "0"+position, Toast.LENGTH_SHORT).show();

                }else if (position == 1){
                    Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                }
                index = position;
                adapter.setIndex(index);
                adapter.notifyDataSetChanged();

            }
        });

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
