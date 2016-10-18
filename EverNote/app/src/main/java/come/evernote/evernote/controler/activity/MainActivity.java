package come.evernote.evernote.controler.activity;


import android.view.View;
import android.widget.ImageView;

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
    public void onClick(View v) {

    }
}
