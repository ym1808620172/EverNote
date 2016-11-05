package come.evernote.evernote.controler.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.Interfaces.MenuDeleteI;
import come.evernote.evernote.controler.adapter.DrawerAdapter;
import come.evernote.evernote.controler.fragment.AllTextNotesFragment;
import come.evernote.evernote.controler.fragment.TextNotesBookFragment;
import come.evernote.evernote.controler.fragment.WasteBinFragment;
import come.evernote.evernote.model.bean.DrawerShowBean;


/**
 * Created by dllo on 16/10/17.
 * Activity基类
 *
 * @author sunhongxu
 */
public abstract class AbsBaseActivity extends AppCompatActivity implements AMapLocationListener, PopupMenu.OnMenuItemClickListener {
    public FrameLayout contentView;//标题栏里的占位布局
    private static ImageView rightImg;//标题栏右侧图片
    private static ImageView leftImg;//标题栏左侧图片
    private static ImageView midImg;//标题栏中间图片
    private static TextView titltTv;//标题栏文字
    private View titlebar;//标题栏布局
    private static ImageView drawerImg;//打开抽屉的图片
    private List<DrawerShowBean> datas;
    private int index;
    private DrawerAdapter adapter;
    private ListView drawerLv;// 抽屉lv
    private ImageView forImg;
    private DrawerLayout rootView;// 整个页面的布局对象
    private LinearLayout layout;// 抽屉布局对象
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;
    private boolean is = false;
    private Animation animation;
    private TextView forTv;
    private MenuDeleteI deleteI;
    private WasteBinFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_tiitle);
        int titlebarResId = getTitlebarResId();
        setIfTitles(titlebarResId);
        contentView = (FrameLayout) findViewById(R.id.base_contentview);
        contentView.addView(View.inflate(this, setLayout(), null));
        //制定流程
        initView();
        adapter = new DrawerAdapter(AbsBaseActivity.this);
        forImg = byView(R.id.for_img);
        drawerLv = byView(R.id.drawer_lv);
        rootView = byView(R.id.root_view);
        layout = byView(R.id.drawer_view);
        forTv = byView(R.id.for_tv);
        forTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCity();
            }
        });
        fragment = WasteBinFragment.newInstance();
        //设置抽屉
        setDrawer();
        // 头布局
        getHead();
        initDatas();
    }

    private void setCity() {
        CityPicker cityPicker = new CityPicker.Builder(AbsBaseActivity.this).textSize(20)
                .onlyShowProvinceAndCity(false)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(true)
                .districtCyclic(true)
                .visibleItemsCount(7)
                .itemPadding(10)
                .build();

        cityPicker.show();
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                forTv.setText("省：" + citySelected[0] + " " + "市：" + citySelected[1] + "区:" + " " + citySelected[2]);
            }
        });
    }

    private void getHead() {
        View view = getLayoutInflater().inflate(R.layout.first_page_drawer_header, null);
        drawerLv.addHeaderView(view);
    }

    private void setDrawer() {
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

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (position == 8) {
                    Toast.makeText(AbsBaseActivity.this, "8", Toast.LENGTH_SHORT).show();
                    // 跳转设置页面
                    goTo(AbsBaseActivity.this, SettingActivity.class);
                } else if (position == 2) {
                    transaction.replace(R.id.main_frame_layout, TextNotesBookFragment.newInstance());
                    manager.popBackStack();
                    transaction.addToBackStack(null);
                    transaction.commit();
                    rootView.closeDrawer(layout);
                } else if (position == 5) {
                    transaction.replace(R.id.main_frame_layout, fragment);
                    manager.popBackStack();
                    transaction.addToBackStack(null);
                    deleteI = fragment;
                    transaction.commit();
                    rootView.closeDrawer(layout);
                } else if (position == 1) {
                    transaction.replace(R.id.main_frame_layout, AllTextNotesFragment.newInstance());
                    manager.popBackStack();
                    transaction.commit();
                    rootView.closeDrawer(layout);
                }
                MainActivity.FloatingBtnShow(position);
                index = position - 1;
                adapter.setIndex(index);
                adapter.notifyDataSetChanged();
            }
        });

        // 动画旋转
        forImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置定位
                getPositon();
                animation = AnimationUtils.loadAnimation(AbsBaseActivity.this, R.anim.animset);
                forImg.startAnimation(animation);
                forTv.setText("同步时间");
            }
        });

    }

    private void getPositon() {
        //声明mLocationOption对象
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    protected void electricQuantity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected void setIfTitles(int titlebarResId) {
        if (titlebarResId != 0) {
            titlebar = findViewById(R.id.base_titlebar);
            titlebar.setVisibility(View.GONE);
        } else {
            titlebar = findViewById(R.id.base_titlebar);
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
                    PopupMenu popupMenu = new PopupMenu(AbsBaseActivity.this, v);
                    popupMenu.getMenuInflater().inflate(R.menu.activity_main_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(AbsBaseActivity.this);
                    popupMenu.show();
                    onClickRight(v);
                }
            });
            midImg = (ImageView) findViewById(R.id.base_title_search_img);
            midImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickMid();
                }
            });
            drawerImg = (ImageView) findViewById(R.id.base_title_drawer_img);
            drawerImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickDrawer();
                    rootView.openDrawer(layout);
                }
            });
            titltTv = (TextView) findViewById(R.id.base_title_tv);
            titlebar.setVisibility(View.VISIBLE);
        }
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
     * 打开抽屉的点击事件
     */
    protected abstract void onClickDrawer();

    /**
     * 右侧图片的点击事件
     *
     * @param v
     */
    protected abstract void onClickRight(View v);

    /**
     * 中间图片的点击事件
     */
    protected abstract void onClickMid();

    /**
     * 左侧图片的点击事件
     */
    protected abstract void onClickLeft();


    /**
     * 不带返回值跳转
     *
     * @param from 当前界面
     * @param to   目标界面
     */
    protected void goTo(Context from, Class<? extends AbsBaseActivity> to) {
        startActivity(new Intent(from, to));
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
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
     * 设置左侧图片显示与隐藏
     *
     * @param visible
     */
    public static void setLeftImgVisible(Boolean visible) {
        if (leftImg != null) {
            if (visible) {
                leftImg.setVisibility(View.VISIBLE);
            } else {
                leftImg.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 设置左侧图片显示与隐藏
     *
     * @param visible
     */
    public static void setDrawerImgVisible(Boolean visible) {
        if (drawerImg != null) {
            if (visible) {
                drawerImg.setVisibility(View.VISIBLE);
            } else {
                drawerImg.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 设置右侧图片显示与隐藏
     *
     * @param visible
     */
    public static void setRightImgVisible(Boolean visible) {
        if (rightImg != null) {
            if (visible) {
                rightImg.setVisibility(View.VISIBLE);
            } else {
                rightImg.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 设置中间图片显示与隐藏
     *
     * @param visible
     */
    public static void setMidImgVisible(Boolean visible) {
        if (midImg != null) {
            if (visible) {
                midImg.setVisibility(View.VISIBLE);
            } else {
                midImg.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取自定义标题栏
     * 如果子类复写并返回不等于0的布局文件，将会覆盖默认标题
     * 返回0 将会采用默认标题
     *
     * @return
     */
    protected int getTitlebarResId() {
        return 0;
    }

    /**
     * 设置中间标题
     *
     * @param title
     */
    public static void setTitle(String title) {
        if (titltTv != null) {
            if (titltTv != null) {
                titltTv.setText(title);
            }
        }
    }

    /**
     * 设置抽屉图片的样式
     *
     * @param imgResource
     */
    public static void setDrawerImg(int imgResource) {
        if (drawerImg != null) {
            if (drawerImg != null) {
                drawerImg.setImageResource(imgResource);
            }
        }
    }

    /**
     * 设置左边图片的样式
     *
     * @param imgResource
     */
    public static void setLeftImg(int imgResource) {
        if (leftImg != null) {
            if (leftImg != null) {
                leftImg.setImageResource(imgResource);
            }
        }
    }

    /**
     * 设置右边图片的样式
     *
     * @param imgResource
     */
    public static void setRightImg(int imgResource) {
        if (rightImg != null) {
            if (rightImg != null) {
                rightImg.setImageResource(imgResource);
            }
        }
    }

    /**
     * 设置中间图片的样式
     *
     * @param imgResource
     */
    public static void setMidImg(int imgResource) {
        if (midImg != null) {
            if (midImg != null) {
                midImg.setImageResource(imgResource);
            }
        }
    }

    public View getTitleBar() {
        return titlebar;
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                if (is = true) {
                    forTv.setText(aMapLocation.getCity() + " " + df.format(date));
                }
                forTv.setText(aMapLocation.getCity());
                mlocationClient.stopLocation();
                forImg.clearAnimation();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.d("aaaa", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                mlocationClient.stopLocation();
            }
        }
    }

    public String getCityText() {
        Log.d("dddw", forTv.getText().toString() + "");
        return forTv.getText().toString();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu_select:
                if (deleteI!=null){
                    deleteI.onDeleteWasteBin(1);
                }
                break;
            case R.id.main_menu_add:
                if (deleteI!=null){
                    deleteI.onDeleteWasteBin(2);
                }
                break;
            case R.id.main_menu_rank:
                break;
            case R.id.main_menu_check:
                break;
            case R.id.main_menu_synchronization:
                break;
            case R.id.main_menu_setting:
                break;
        }
        return false;
    }
}
