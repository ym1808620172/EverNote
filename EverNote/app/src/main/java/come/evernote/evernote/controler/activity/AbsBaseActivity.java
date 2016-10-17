package come.evernote.evernote.controler.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import come.evernote.evernote.R;


/**
 * Created by dllo on 16/10/17.
 * Activity基类
 *
 * @author sunhongxu
 */
public abstract class AbsBaseActivity extends AppCompatActivity implements View.OnClickListener {
    public ViewGroup contentView;
    private TextView rightBtn;
    private View leftBtn;
    private TextView titltTv;
    private View titlebar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_tiitle);
        int titlebarResId = getTitlebarResId();
        if (titlebarResId!=0) {
            LinearLayout view=(LinearLayout) findViewById(R.id.base_view);
            view.removeViewAt(0);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ViewGroup titleView=(ViewGroup) View.inflate(this, titlebarResId, null);
            view.addView(titleView, 0,lp);
            view.setBackgroundDrawable(titleView.getBackground());
            titlebar=titleView;
        }else {
            titlebar=findViewById(R.id.base_titlebar);
            leftBtn = findViewById(R.id.base_back_btn);
            leftBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onClickLeft();
                }
            });
            rightBtn = (TextView) findViewById(R.id.base_menu_btn);
            rightBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onClickRight();
                }
            });
            titltTv=(TextView) findViewById(R.id.base_title_tv);
        }

        contentView=(ViewGroup) findViewById(R.id.base_contentview);
        contentView.addView(View.inflate(this, setLayout(), null));
        setRightBtnVisible(false);
        //制定流程
        initView();
        initDatas();

    }
    /**
     * 加载布局
     *
     * @return R.layout.xx
     */
    protected abstract int setLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 加载数据
     */
    protected abstract void initDatas();

    /**
     * findViewById
     *
     * @param resId 控件ID
     * @param <T>   控件
     */
    protected <T extends View> T byView(int resId) {
        return (T) findViewById(resId);
    }

    /**
     * 不带返回值跳转
     *
     * @param from 当前界面
     * @param to   目标界面
     */
    protected void goTo(Context from, Class<? extends AbsBaseActivity> to) {
        startActivity(new Intent(from, to));
    }

    protected void goTo(Context from, Class<? extends AbsBaseActivity> to, Bundle extras) {
        Intent intent = new Intent(from, to);
        intent.putExtras(extras);
        startActivity(intent);

    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 点击左侧按钮
     * 默认是退出
     */
    protected void onClickLeft() {
        finish();
    }

    /**
     * 点击右侧按钮
     * 默认什么都不做
     */
    protected void onClickRight() {

    }

    /**
     * 设置左侧按钮显示与隐藏
     * @param visible
     */
    public void setLeftBtnVisible(Boolean visible) {
        if (leftBtn!=null) {
            if (visible) {
                leftBtn.setVisibility(View.VISIBLE);
            }else{
                leftBtn.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 设置右侧按钮显示与隐藏
     * @param visible
     */
    public void setRightBtnVisible(Boolean visible) {
        if (rightBtn!=null) {
            if (visible) {
                rightBtn.setVisibility(View.VISIBLE);
            }else{
                rightBtn.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 获取自定义标题栏
     * 如果子类复写并返回不等于0的布局文件，将会覆盖默认标题
     * 返回0 将会采用默认标题
     * @return
     */
    protected int getTitlebarResId() {
        return 0;
    }

    /**
     * 设置中间标题
     * @param title
     */
    public void setTitle(String title){
        if (titltTv!=null) {
            if (titltTv!=null) {
                titltTv.setText(title);
            }
        }
    }


    /**
     * 设置右边你按钮文字属性
     * @param title
     */
    public void setRtTitle(String title){
        if (rightBtn!=null) {
            rightBtn.setText(title);
        }
    }

    public View getTitleBar(){

        return titlebar;
    }


}
