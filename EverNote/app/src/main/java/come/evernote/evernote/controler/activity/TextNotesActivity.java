package come.evernote.evernote.controler.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.adapter.AttachDrawerAdapter;
import come.evernote.evernote.controler.fragment.TextNotesAboutFragment;
import come.evernote.evernote.controler.fragment.TextNotesMenuFragment;
import come.evernote.evernote.model.bean.AttachDrawerBean;
import come.evernote.evernote.model.bean.PhotoBean;

/**
 * Created by dllo on 16/10/17.
 * 文字笔记界面
 *
 * @author 杜显东
 */
public class TextNotesActivity extends AbsBaseActivity {
    //标题栏
    private ImageView aboutTv;//介绍(圈I)
    private ImageView menuIv;//菜单(三个点)
    private ImageView formattingIv;//标题改变字体(A)图片
    private ImageView doneIv;//完成保存
    private ImageView redoIv;//左撤销
    private ImageView underIv;//右撤销
    private ImageView attachIv;//附加
    private ImageView penThinIv;//笔薄
    private ImageView shareIv;//分享
    //抽屉
    private DrawerLayout drawerLayout;//根布局
    private LinearLayout layout;//抽屉的布局
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private EditText contentEt;//输入的内容
    private LinearLayout attachLayout;//附件Dialog
    private ListView listView;//附件Dialog的ListView
    private BottomSheetBehavior mBehavior;
    private List<AttachDrawerBean> data;
    private AttachDrawerAdapter attachAdapter;

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
    private SpannableString span;


    @Override
    protected int setLayout() {
        return R.layout.activity_notes_text;
    }

    @Override
    protected void initView() {
        aboutTv = byView(R.id.notes_text_about_img);
        menuIv = byView(R.id.item_text_notes_menu_img);
        drawerLayout = byView(R.id.drawer_layout);
        layout = byView(R.id.drawer_liner_layout);
        formattingIv = byView(R.id.item_text_notes_formatting_img);
        contentEt = byView(R.id.notes_text_content_et);
        doneIv = byView(R.id.item_text_notes_done_iv);
        underIv = byView(R.id.item_text_notes_left_undo_iv);
        redoIv = byView(R.id.item_text_notes_right_redo_iv);
        attachIv = byView(R.id.item_text_notes_attach_iv);
        penThinIv = byView(R.id.item_text_notes_pen_thin_img);
        shareIv = byView(R.id.item_text_notes_share_iv);
        attachLayout = byView(R.id.bottomSheet);
        listView = byView(R.id.attach_drawer_bottom_list_view);
        menuIv.setOnClickListener(this);
        aboutTv.setOnClickListener(this);
        formattingIv.setOnClickListener(this);
        doneIv.setOnClickListener(this);
        redoIv.setOnClickListener(this);
        underIv.setOnClickListener(this);
        attachIv.setOnClickListener(this);
        penThinIv.setOnClickListener(this);
        shareIv.setOnClickListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//抽屉关闭手势滑动

    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        if (intent != null) {
            PhotoBean bean = (PhotoBean) intent.getSerializableExtra("photo");
            if (bean != null) {
                Bitmap bitmap = getBitmap(bean.getBitmap());
            }
            PhotoBean imgBean = (PhotoBean) intent.getSerializableExtra("img");
            if (imgBean != null){
                Bitmap bitmap = getBitmap(imgBean.getBitmap());
                Log.d("xxx", "bitmap:" + bitmap);
            }
        }
        setIfTitles(1);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
        String string = bundle.getString("key");
            if (string != null && string.equals("1")){
            goTo(TextNotesActivity.this, PenThinActivity.class);

            }

        }
        mBehavior = BottomSheetBehavior.from(attachLayout);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        attachAdapter = new AttachDrawerAdapter(this);
        data = new ArrayList<>();
        data.add(new AttachDrawerBean("相册",R.mipmap.xiangce));
        data.add(new AttachDrawerBean("文件",R.mipmap.wenjian));
        data.add(new AttachDrawerBean("视频文件",R.mipmap.shipin));
        data.add(new AttachDrawerBean("录音文件",R.mipmap.luyin));
        data.add(new AttachDrawerBean("拍摄照片",R.mipmap.xiangji));
        data.add(new AttachDrawerBean("录制音频",R.mipmap.yinpin));
        data.add(new AttachDrawerBean("手写",R.mipmap.shouxei));
        attachAdapter.setData(data);
        listView.setAdapter(attachAdapter);

        Log.d("zzz", "mBehavior.getState():" + mBehavior);
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
        span = new SpannableString(contentEt.getText().toString());
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
                    createWindow();//popupWindow
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
                    span.setSpan(new StyleSpan(Typeface.BOLD), 0, contentEt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        contentEt.setText(span);
                    contentEt.setMovementMethod(LinkMovementMethod.getInstance());
                    isClick = true;
                } else if (isClick) {
                    boldIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_formatting_popup_window_italic_iv:
                if (!isClick) {
                    italicIv.setSelected(true);
                    span.setSpan(new StyleSpan(Typeface.ITALIC), 0, contentEt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                    contentEt.setText(span);
                    isClick = true;
                } else if (isClick) {
                    italicIv.setSelected(false);
                    isClick = false;

                }
                break;
            case R.id.item_formatting_popup_window_underline_iv:
                if (!isClick) {
                    underlineIv.setSelected(true);
                    span.setSpan(new UnderlineSpan(), 0, contentEt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    contentEt.setText(span);
                    isClick = true;

                } else if (isClick) {
                    underlineIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_formatting_popup_window_line_through_iv:
                if (!isClick) {
                    lineThroughIv.setSelected(true);
                    span.setSpan(new StrikethroughSpan(), 0, contentEt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    isClick = true;

                } else if (isClick) {
                    lineThroughIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_formatting_popup_window_background_iv:
                if (!isClick) {
                    backgroundIv.setSelected(true);
                    span.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, contentEt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                    contentEt.setText(span);
                    isClick = true;

                } else if (isClick) {
                    backgroundIv.setSelected(false);
                    isClick = false;

                }
                break;
            case R.id.item_formatting_popup_window_checkbox_iv:

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
            case R.id.item_text_notes_done_iv:
                if (!isPopupClick) {
                    doneIv.setSelected(true);
                    isPopupClick = true;
                } else if (isPopupClick) {
                    doneIv.setSelected(false);
                    isPopupClick = false;
                }
                break;
            case R.id.item_text_notes_left_undo_iv:
                if (!isPopupClick) {
                    underIv.setSelected(true);
                    isPopupClick = true;
                } else if (isPopupClick) {
                    underIv.setSelected(false);
                    isPopupClick = false;
                }

                break;
            case R.id.item_text_notes_right_redo_iv:
                if (!isPopupClick) {
                    redoIv.setSelected(true);
                    isPopupClick = true;
                } else if (isPopupClick) {
                    redoIv.setSelected(false);
                    isPopupClick = false;
                }
                break;
            case R.id.item_text_notes_attach_iv:
//                Log.d("aaa", "mBehavior.111():" + mBehavior.getState()+"aa");
                if (!isPopupClick) {
                    attachIv.setSelected(true);
                    if (mBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }

                    isPopupClick = true;
                } else if (isPopupClick) {
                    attachIv.setSelected(false);
                    isPopupClick = false;
                        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            case R.id.item_text_notes_pen_thin_img:
                goTo(TextNotesActivity.this, PenThinActivity.class);
                break;
            case R.id.item_text_notes_share_iv:
                break;
        }
        contentEt.setText(span);

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


}
