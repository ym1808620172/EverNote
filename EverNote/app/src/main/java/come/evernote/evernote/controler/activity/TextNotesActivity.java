package come.evernote.evernote.controler.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.fragment.TextNotesAboutFragment;
import come.evernote.evernote.controler.fragment.TextNotesMenuFragment;
import come.evernote.evernote.model.bean.PhotoBean;

/**
 * Created by dllo on 16/10/17.
 * 文字笔记界面
 *
 * @author 杜显东
 */
public class TextNotesActivity extends AbsBaseActivity {
    private ImageView windowIV;//圈I
    private ImageView aboutTv;//圈I
    private ImageView menuIv;//菜单(三个点)
    private DrawerLayout drawerLayout;//根布局
    private LinearLayout layout;//抽屉的布局
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ImageView formattingIv;//标题改变字体(A)图片
    //popupWindow
    private PopupWindow popupWindow;
    private ImageView boldIv;//加粗
    private ImageView italicIv;//斜体
    private ImageView underlineIv;//下划线
    private ImageView lineThroughIv;//中划线
    private ImageView backgroundIv;  //背景
    private ImageView checkboxIv;//勾选按钮
    private ImageView orderlyIv;//有序排列
    private ImageView disorderIv;//无序排列
    private ImageView leftIv;//右侧缩进
    private ImageView rightIv;//左侧缩进


    @Override
    protected int setLayout() {
        electricQuantity();
        return R.layout.activity_notes_text;
    }

    @Override
    protected void initView() {
        aboutTv = byView(R.id.notes_text_about_img);
        menuIv = byView(R.id.item_text_notes_menu_img);
        drawerLayout = byView(R.id.drawer_layout);
        layout = byView(R.id.drawer_liner_layout);
        formattingIv = byView(R.id.item_text_notes_formatting_img);
        menuIv.setOnClickListener(this);
        aboutTv.setOnClickListener(this);
        formattingIv.setOnClickListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//抽屉关闭手势滑动

    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        if (intent != null) {
            PhotoBean bean = (PhotoBean) intent.getSerializableExtra("photo");
            if (bean != null) {
                Bitmap bitmap = getBitmap(bean.getBitmap());
                Log.d("aaaa", "bitmap:" + bitmap);
            }
        }
        setIfTitles(1);
    }

    public static Bitmap getBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
    }

    @Override
    protected void onClickDrawer() {

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

    private boolean isClick = false;
    private boolean isPopupClick = false;

    @Override
    public void onClick(View view) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.item_text_notes_menu_img:
                transaction.replace(R.id.drawer_frame_layout, new TextNotesMenuFragment()).commit();
                drawerLayout.openDrawer(layout);
                break;
            case R.id.notes_text_about_img:
                transaction.replace(R.id.drawer_frame_layout, new TextNotesAboutFragment()).commit();
                drawerLayout.openDrawer(layout);
                break;
            case R.id.item_text_notes_formatting_img:
                if (!isPopupClick) {
                    formattingIv.setSelected(true);
                    createWindow();
                    isPopupClick = true;
                } else if (isPopupClick) {
                    formattingIv.setSelected(false);
                    popupWindow.dismiss();
                    isPopupClick = false;
                }
                break;
            case R.id.item_formatting_popup_window_bold_iv:
                if (!isClick) {
                    boldIv.setSelected(true);
                    isClick = true;
                } else if (isClick) {
                    boldIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_formatting_popup_window_italic_iv:
                if (!isClick) {
                    italicIv.setSelected(true);
                    isClick = true;
                } else if (isClick) {
                    italicIv.setSelected(false);
                    isClick = false;

                }
                break;
            case R.id.item_formatting_popup_window_underline_iv:
                if (!isClick) {
                    underlineIv.setSelected(true);
                    isClick = true;

                } else if (isClick) {
                    underlineIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_formatting_popup_window_line_through_iv:
                if (!isClick) {
                    lineThroughIv.setSelected(true);
                    isClick = true;

                } else if (isClick) {
                    lineThroughIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_formatting_popup_window_background_iv:
                if (!isClick) {
                    backgroundIv.setSelected(true);
                    isClick = true;

                } else if (isClick) {
                    backgroundIv.setSelected(false);
                    isClick = false;

                }
                break;
            case R.id.item_formatting_popup_window_checkbox_iv:
                if (!isClick) {


                } else if (isClick) {

                }
                break;
            case R.id.item_formatting_popup_window_orderly_iv:
                if (!isClick) {
                    orderlyIv.setSelected(true);
                    isClick = true;

                } else if (isClick) {
                    orderlyIv.setSelected(false);
                    isClick = false;

                }
                break;
            case R.id.item_formatting_popup_window_disorder_iv:
                if (!isClick) {
                    disorderIv.setSelected(true);
                    isClick = true;

                } else if (isClick) {
                    disorderIv.setSelected(false);
                    isClick = false;

                }
                break;
            case R.id.item_formatting_popup_window_left_iv:
                if (!isClick) {
                    leftIv.setSelected(true);
                    isClick = true;

                } else if (isClick) {
                    leftIv.setSelected(false);
                    isClick = false;

                }
                break;
            case R.id.item_formatting_popup_window_right_iv:
                if (!isClick) {
                    rightIv.setSelected(true);
                    isClick = true;

                } else if (isClick) {
                    rightIv.setSelected(false);
                    isClick = false;

                }
                break;

        }


    }

    /**
     * 弹出PopupWindow
     */
    private void createWindow() {
        popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = getLayoutInflater().inflate(R.layout.item_formatting_popu_window, null);
        boldIv = (ImageView) view.findViewById(R.id.item_formatting_popup_window_bold_iv);
        italicIv = (ImageView) view.findViewById(R.id.item_formatting_popup_window_italic_iv);
        underlineIv = (ImageView) view.findViewById(R.id.item_formatting_popup_window_underline_iv);
        lineThroughIv = (ImageView) view.findViewById(R.id.item_formatting_popup_window_line_through_iv);
        backgroundIv = (ImageView) view.findViewById(R.id.item_formatting_popup_window_background_iv);
        checkboxIv = (ImageView) view.findViewById(R.id.item_formatting_popup_window_checkbox_iv);
        orderlyIv = (ImageView) view.findViewById(R.id.item_formatting_popup_window_orderly_iv);
        disorderIv = (ImageView) view.findViewById(R.id.item_formatting_popup_window_disorder_iv);
        leftIv = (ImageView) view.findViewById(R.id.item_formatting_popup_window_left_iv);
        rightIv = (ImageView) view.findViewById(R.id.item_formatting_popup_window_right_iv);
        boldIv.setOnClickListener(this);
        italicIv.setOnClickListener(this);
        underlineIv.setOnClickListener(this);
        lineThroughIv.setOnClickListener(this);
        backgroundIv.setOnClickListener(this);
        checkboxIv.setOnClickListener(this);
        orderlyIv.setOnClickListener(this);
        disorderIv.setOnClickListener(this);
        leftIv.setOnClickListener(this);
        rightIv.setOnClickListener(this);

        popupWindow.setContentView(view);
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(formattingIv, Gravity.BOTTOM, 100, 100);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}
