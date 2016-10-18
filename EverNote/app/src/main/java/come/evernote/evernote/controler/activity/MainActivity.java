package come.evernote.evernote.controler.activity;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.fragment.TextNotesFragment;

public class MainActivity extends AbsBaseActivity implements View.OnClickListener {
    private ImageView imageView;//菜单图片
    private FragmentManager manager;//管理者
    private FragmentTransaction transaction;

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
    public void onClick(View view) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()){
            case R.id.main_img:
                transaction.replace(R.id.main_frame_layout,new TextNotesFragment()).commit();
                break;
        }

    }
}
