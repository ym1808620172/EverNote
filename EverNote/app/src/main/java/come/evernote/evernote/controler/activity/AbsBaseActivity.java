package come.evernote.evernote.controler.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import come.evernote.evernote.R;


/**
 * Created by dllo on 16/10/17.
 * Activity基类
 *
 * @author sunhongxu
 */
public abstract class AbsBaseActivity extends AppCompatActivity implements View.OnClickListener {
    public ViewGroup contentView;
    private ImageView rightImg;
    private ImageView leftImg;
    private ImageView midImg;
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
            leftImg = (ImageView) findViewById(R.id.base_title_msg_img);
            leftImg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onClickLeft();
                }
            });
            rightImg = (ImageView) findViewById(R.id.base_title_menu_img);
            rightImg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onClickRight();
                }
            });
            midImg = (ImageView) findViewById(R.id.base_title_search_img);
            midImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickMid();
                }
            });
            titltTv=(TextView) findViewById(R.id.base_title_tv);
        }

        contentView=(ViewGroup) findViewById(R.id.base_contentview);
        contentView.addView(View.inflate(this, setLayout(), null));
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
     * 默认什么都不做
     */
    protected void onClickLeft() {
    }

    /**
     * 点击左侧图片
     * 默认什么都不做
     */
    private void onClickMid() {
    }
    /**
     * 点击右侧按钮
     * 默认什么都不做
     */
    protected void onClickRight() {

    }

    /**
     * 设置左侧图片显示与隐藏
     * @param visible
     */
    public void setLeftImgVisible(Boolean visible) {
        if (leftImg!=null) {
            if (visible) {
                leftImg.setVisibility(View.VISIBLE);
            }else{
                leftImg.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 设置右侧图片显示与隐藏
     * @param visible
     */
    public void setRightImgVisible(Boolean visible) {
        if (rightImg!=null) {
            if (visible) {
                rightImg.setVisibility(View.VISIBLE);
            }else{
                rightImg.setVisibility(View.GONE);
            }
        }

    }
    /**
     * 设置中间图片显示与隐藏
     * @param visible
     */
    public void setMidImgVisible(Boolean visible) {
        if (midImg!=null) {
            if (visible) {
                midImg.setVisibility(View.VISIBLE);
            }else{
                midImg.setVisibility(View.GONE);
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

    public View getTitleBar(){
        return titlebar;
    }


}
