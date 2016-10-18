package come.evernote.evernote.controler.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by dllo on 16/10/17.
 * Activity基类
 * @author sunhongxu
 */
public abstract class AbsBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        intiView();
        initData();
    }

    /**
     * 绑定布局
     *
     * @return R.layout.xx
     */
    protected abstract int setLayout();
    /**
     * 初始化组件
     */

    protected abstract void intiView();

    /**
     * 加载数据
     */
    protected abstract void initData();
    /**
     * findViewById
     *
     * @param resId 控件Id
     * @return 控件
     */
    protected <T extends View> T byView(int resId) {
        return (T) findViewById(resId);
    }

    /**
     * Activity的跳转
     *
     * @param from 当前界面
     * @param to   目标界面
     */
    protected void goTo(Context from, Class<? extends AbsBaseActivity> to) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
    }
    /**
     * Activity的带返回值跳转
     *
     * @param from 当前界面
     * @param to   目标界面
     */
    protected void goTo(Context from, Class<? extends AbsBaseActivity> to, Bundle bundle) {
        Intent intent = new Intent(from, to);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
