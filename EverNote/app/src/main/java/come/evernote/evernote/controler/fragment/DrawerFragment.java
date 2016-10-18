package come.evernote.evernote.controler.fragment;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.Interfaces.DrawerOnClick;


/**
 * Created by dllo on 16/10/17.
 * 主页面中的抽屉
 */
public class DrawerFragment extends AbsBaseFragment implements View.OnClickListener {

    private Button openBtn;
    private  DrawerOnClick drawerOnClick;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        drawerOnClick  = (DrawerOnClick) context;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_drawer;
    }

    @Override
    protected void initView() {
        openBtn = byView(R.id.drawer_btn);

    }

    @Override
    protected void initData() {
        openBtn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        drawerOnClick.draweronclick(0);
    }


}
