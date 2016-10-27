package come.evernote.evernote.controler.activity;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
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
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.adapter.DrawerAdapter;
import come.evernote.evernote.model.bean.DrawerShowBean;
import come.evernote.evernote.model.bean.PhotoBean;

public class MainActivity extends AbsBaseActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener, AMapLocationListener {
    private DrawerLayout rootView;// 整个页面的布局对象
    private LinearLayout layout;// 抽屉布局对象
    private List<DrawerShowBean> datas;
    private DrawerAdapter adapter;
    private ListView drawerLv;// 抽屉lv
    private int index;
    private TextView forTv;
    private ImageView forImg;
    private RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfabHelper;
    private RapidFloatingActionLayout rfaLayout;
    private static final int CAMERA_WITH_DATA = 3023;

    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;
    private boolean is = false;
    private Animation animation;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        rootView = byView(R.id.root_view);
        layout = byView(R.id.drawer_view);
        drawerLv = byView(R.id.drawer_lv);
        forTv = byView(R.id.for_tv);
        rfaLayout = byView(R.id.label_list_sample_rfal);
        rfaButton = byView(R.id.label_list_sample_rfab);
        forImg = byView(R.id.for_img);
        forTv.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        // 头布局
        getHead();
        adapter = new DrawerAdapter(this);
        //设置抽屉
        setDrawer();
        //设置卫星菜单
        setFloatingBtn();
        // 设置定位
        getPositon();


    }

    private void setFloatingBtn() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("拍照")
                .setResId(R.mipmap.ic_list_bar_camera)
                .setIconNormalColor(Color.GREEN)
                .setIconPressedColor(Color.GREEN)
                .setLabelColor(Color.GREEN)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("附件")
                .setResId(R.mipmap.ic_list_bar_attachment)
                .setIconNormalColor(Color.RED)
                .setIconPressedColor(Color.RED)
                .setLabelColor(Color.RED)
                .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("录音")
                .setResId(R.mipmap.ic_list_bar_audio)
                .setIconNormalColor(0xff283593)
                .setIconPressedColor(0xff1a237e)
                .setLabelColor(0xff283593)
                .setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("提醒")
                .setResId(R.mipmap.ic_list_bar_reminder)
                .setIconNormalColor(0xff056f00)
                .setIconPressedColor(0xff0d5302)
                .setLabelColor(0xff056f00)
                .setWrapper(3)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel("手写")
                .setResId(R.mipmap.ic_list_bar_handwriting)
                .setIconNormalColor(0xff4e342e)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(Color.WHITE)
                .setLabelSizeSp(14)
                .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(this, 4)))
                .setWrapper(4)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("文字笔记")
                .setResId(R.mipmap.ic_list_bar_text_note)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(5)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(this, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(this, 5))
        ;
        rfabHelper = new RapidFloatingActionHelper(this, rfaLayout, rfaButton, rfaContent).build();

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
                if (position == 8) {
                    Toast.makeText(MainActivity.this, "8", Toast.LENGTH_SHORT).show();
                    // 跳转设置页面
                    goTo(MainActivity.this, SettingActivity.class);
                }
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
                animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.animset);
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


    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                if (is = true) {
                    forTv.setText(amapLocation.getCity() + " " + df.format(date));
                }
                forTv.setText(df.format(date));
                mlocationClient.stopLocation();
                forImg.clearAnimation();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.d("aaaa", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
                mlocationClient.stopLocation();
            }
        }
    }


    private void getHead() {
        View view = getLayoutInflater().inflate(R.layout.first_page_drawer_header, null);
        drawerLv.addHeaderView(view);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.for_tv:
                CityPicker cityPicker = new CityPicker.Builder(MainActivity.this).textSize(20)
                        .onlyShowProvinceAndCity(true)
                        .confirTextColor("#000000")
                        .cancelTextColor("#000000")
                        .province("江苏省")
                        .city("常州市")
                        .district("新北区")
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
                        forTv.setText("省：" + citySelected[0] + "市：" + citySelected[1]  );
                    }
                });
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


    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_WITH_DATA);
                break;
            case 4:

                bundle.putString("key", "1");
                goTo(MainActivity.this, TextNotesActivity.class, bundle);
                break;
            case 5:
                goTo(MainActivity.this, TextNotesActivity.class);
                break;
            case 2:
                bundle.putInt("index", 2);
                goTo(MainActivity.this, TextNotesActivity.class, bundle);
                break;
            case 3:
                intent = new Intent(MainActivity.this, RemendPopActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intentActtchenment = new Intent(Intent.ACTION_GET_CONTENT);
                intentActtchenment.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intentActtchenment.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intentActtchenment, 1);
                break;

        }
        rfabHelper.toggleContent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case CAMERA_WITH_DATA:
                final Bitmap photo = data.getParcelableExtra("data");
                //实例化字节数组输出流
                if (photo != null) {
                    getBytes(photo);
                    //实现字节流序列化你并传值
                    Bundle bundle = new Bundle();
                    PhotoBean bean = new PhotoBean();
                    bean.setBitmap(getBytes(photo));
                    bundle.putSerializable("photo", bean);
                    goTo(MainActivity.this, TextNotesActivity.class, bundle);
                }
                break;
            case 1:
                if (data != null && data.getData() != null) {
                    Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
                    int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    actualimagecursor.moveToFirst();
                    String img_path = actualimagecursor.getString(actual_image_column_index);
                    if (img_path != null) {
                        File file = new File(img_path);
                        String path = getFileName(file.toString());
                        Intent intent = new Intent(MainActivity.this, TextNotesActivity.class);
                        intent.putExtra("text", path);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    private String getFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        if (start != -1) {
            return pathandname.substring(start + 1, pathandname.length());
        } else {
            return null;
        }
    }


    public static byte[] getBytes(Bitmap bitmap) {
        //实例化字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);//压缩位图
        return baos.toByteArray();//创建分配字节数组
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        switch (position) {
            case 0:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_WITH_DATA);
                break;
        }
        rfabHelper.toggleContent();
    }
}
