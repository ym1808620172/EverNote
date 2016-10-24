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

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.adapter.AttachDrawerAdapter;
import come.evernote.evernote.controler.fragment.TextNotesAboutFragment;
import come.evernote.evernote.controler.fragment.TextNotesMenuFragment;
import come.evernote.evernote.model.bean.AttachDrawerBean;
import come.evernote.evernote.model.bean.PhotoBean;
import come.evernote.evernote.view.PictureAndTextEditorView;
import come.evernote.evernote.view.ViewAnimation;

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
    private LinearLayout rootLl;
    private PictureAndTextEditorView editorView;
    private File file;
    private SpannableString span;


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
        rootLl = byView(R.id.notex_root_ll);
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
        editorView = byView(R.id.notes_text_content_et);

    }

    @Override
    protected void initDatas() {
        rootLl.setAnimation(new ViewAnimation(rootLl.getWidth(), rootLl.getHeight(), 1000));
        Intent intent = getIntent();
        if (intent != null) {
            PhotoBean bean = (PhotoBean) intent.getSerializableExtra("photo");
            if (bean != null) {
                Bitmap bitmap = getBitmap(bean.getBitmap());
                saveCroppedImage(bitmap);
                if (file != null) {
                    editorView.insertBitmap(file.getPath());
                }
            }
            PhotoBean imgBean = (PhotoBean) intent.getSerializableExtra("img");
            if (imgBean != null) {
                Bitmap bitmap = getBitmap(imgBean.getBitmap());
                Log.d("xxx", "bitmap:" + bitmap);
            }
        }
        String editTextContent = editorView.getText().toString();
        setIfTitles(1);
        Log.d("zzz", "editorView.getSelectionStart():" + editorView.getSelectionStart());
        Log.d("zzz", "editorView.length():" + editTextContent.length());
        setSpeaking(editTextContent);

    }

    private void setSpeaking(String editTextContent) {
        int index = editorView.getSelectionStart();
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(TextNotesActivity.this, null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        //如果不需要保存合成音频，注释该行代码
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        //3.开始合成
        mTts.startSpeaking(editTextContent.substring(index, editTextContent.length()), mSynListener);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String string = bundle.getString("key");
            if (string != null && string.equals("1")) {
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
        data.add(new AttachDrawerBean("相册", R.mipmap.xiangce));
        data.add(new AttachDrawerBean("文件", R.mipmap.wenjian));
        data.add(new AttachDrawerBean("视频文件", R.mipmap.shipin));
        data.add(new AttachDrawerBean("录音文件", R.mipmap.luyin));
        data.add(new AttachDrawerBean("拍摄照片", R.mipmap.xiangji));
        data.add(new AttachDrawerBean("录制音频", R.mipmap.yinpin));
        data.add(new AttachDrawerBean("手写", R.mipmap.shouxei));
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
                    contentEt.setText(span);
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
                    contentEt.setText(span);
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
                    contentEt.setText(span);
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
                    contentEt.setText(span);
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
                    contentEt.setText(span);
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
                    if (mBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
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

    private void saveCroppedImage(Bitmap bmp) {
        Date nowTime = new Date(System.currentTimeMillis());
        long timeLong = nowTime.getTime();
        file = new File("/sdcard/myFolder");
        if (!file.exists())
            file.mkdir();

        file = new File(timeLong + ".jpg".trim());
        String fileName = file.getName();
        String mName = fileName.substring(0, fileName.lastIndexOf("."));
        String sName = fileName.substring(fileName.lastIndexOf("."));

        // /sdcard/myFolder/temp_cropped.jpg
        String newFilePath = "/sdcard/myFolder" + "/" + mName + "_cropped" + sName;
        file = new File(newFilePath);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };

}
