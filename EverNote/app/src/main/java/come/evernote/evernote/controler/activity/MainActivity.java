package come.evernote.evernote.controler.activity;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.fragment.AllTextNotesFragment;
import come.evernote.evernote.model.bean.PhotoBean;
import come.evernote.evernote.snake.Snake;

public class MainActivity extends AbsBaseActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {


    private static RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfabHelper;
    private static RapidFloatingActionLayout rfaLayout;
    public static final int CAMERA_WITH_DATA = 3023;

    private FragmentManager manager;
    private FragmentTransaction transaction;



    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        rfaLayout = byView(R.id.label_list_sample_rfal);
        rfaButton = byView(R.id.label_list_sample_rfab);
    }

    @Override
    protected void initDatas() {
        //设置卫星菜单
        setFloatingBtn();
        //设置当前界面
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame_layout, AllTextNotesFragment.newInstance());
        transaction.commit();
        setMidImg(R.mipmap.erweima);
    }
    public static void FloatingBtnShow(int which){
//        if (which==1){
//            rfaButton.setVisibility(View.VISIBLE);
//        }else {
//            rfaButton.setVisibility(View.GONE);
//        }
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
        items.add(new RFACLabelItem<Integer>().setLabel("贪吃蛇")
                .setResId(R.mipmap.icon)
                .setIconNormalColor(0xff283593)
                .setIconPressedColor(0xff283593)
                .setWrapper(6));

        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(this, 6))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(this, 6))
        ;
        rfabHelper = new RapidFloatingActionHelper(this, rfaLayout, rfaButton, rfaContent).build();
    }


    protected void onClickDrawer() {

    }

    @Override
    protected void onClickRight(View v) {

    }

    @Override
    protected void onClickMid() {
        Intent intent = new Intent(getApplication(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

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
            case 6:
                intent = new Intent(MainActivity.this, Snake.class);
                startActivity(intent);
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
                        intent.putExtra("photoUrl", img_path);
                        startActivity(intent);
                    }
                }
                break;
        }

        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    // Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,QrCodeActivity.class);
                    intent.putExtra("result",result);
                    startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }


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
