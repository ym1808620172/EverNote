package come.evernote.evernote.controler.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.adapter.AttachDrawerAdapter;
import come.evernote.evernote.controler.fragment.TextNotesAboutFragment;
import come.evernote.evernote.controler.fragment.TextNotesMenuFragment;
import come.evernote.evernote.model.bean.AttachDrawerBean;
import come.evernote.evernote.model.bean.PhotoBean;
import come.evernote.evernote.model.bean.SaveBean;
import come.evernote.evernote.model.db.LiteOrmInstance;
import come.evernote.evernote.view.BiuEditText;
import come.evernote.evernote.view.PictureAndTextEditorView;
import come.evernote.evernote.view.ViewAnimation;


/**
 * Created by dllo on 16/10/17.
 * 文字笔记界面
 *
 * @author 杜显东
 */
public class TextNotesActivity extends AbsBaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
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
    private ImageView doImg;
    //抽屉
    private DrawerLayout drawerLayout;//根布局
    private LinearLayout layout;//抽屉的布局
    private FragmentManager manager;
    private FragmentTransaction transaction;
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
    private int index;
    private int pictureIndex;
    private SpannableString span;
    private ImageView timeIv;//图片时钟
    private String editTextContent;
    private TextView textView;
    private Intent intent;

    // 录音
    private ImageView btnStart;
    private Chronometer recordChronometer;
    private boolean isStart = false;
    private MediaRecorder mr = null;
    private long recordingTime = 0;// 记录下来的时间
    private long exitTime = 0;
    private LinearLayout noteBook;//我的第一个笔记本
    // 进度对话框
    private ProgressDialog progressDialog;
    private TextView notesBookName;
    private BiuEditText noteTitle;
    private byte[] bitmapList;
    private String format;
    private List<int[]> imgCurse = new ArrayList<>();
    private LinearLayout bookLl;
    private static TextView nameTv;//我的第一个笔记本

    @Override
    protected int setLayout() {
        electricQuantity();
        return R.layout.activity_notes_text;
    }

    @Override
    protected void initView() {
        bookLl = byView(R.id.notes_text_book_layout);
        aboutTv = byView(R.id.notes_text_about_img);
        menuIv = byView(R.id.item_text_notes_menu_img);
        drawerLayout = byView(R.id.drawer_layout);
        layout = byView(R.id.drawer_liner_layout);
        rootLl = byView(R.id.notex_root_ll);
        formattingIv = byView(R.id.item_text_notes_formatting_img);
        doneIv = byView(R.id.item_text_notes_done_iv);
        underIv = byView(R.id.item_text_notes_left_undo_iv);
        redoIv = byView(R.id.item_text_notes_right_redo_iv);
        attachIv = byView(R.id.item_text_notes_attach_iv);
        penThinIv = byView(R.id.item_text_notes_pen_thin_img);
        shareIv = byView(R.id.item_text_notes_share_iv);
        attachLayout = byView(R.id.bottomSheet);
        listView = byView(R.id.attach_drawer_bottom_list_view);
        timeIv = byView(R.id.notes_text_time_img);
        textView = byView(R.id.recoding_chronometer);
        notesBookName = byView(R.id.notes_text_book_name);
        noteTitle = byView(R.id.notes_text_title);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//抽屉关闭手势滑动
        editorView = byView(R.id.notes_text_content_et);
        btnStart = byView(R.id.recording_btn_start);
        recordChronometer = byView(R.id.recoding_chronometer);
        nameTv = byView(R.id.notes_text_book_name);
        EventBus.getDefault().register(this);//注册Eventbus
        setListeren();
    }

    private void setListeren() {
        noteBook = byView(R.id.notes_text_book_layout);
        menuIv.setOnClickListener(this);
        aboutTv.setOnClickListener(this);
        formattingIv.setOnClickListener(this);
        doneIv.setOnClickListener(this);
        redoIv.setOnClickListener(this);
        underIv.setOnClickListener(this);
        attachIv.setOnClickListener(this);
        penThinIv.setOnClickListener(this);
        shareIv.setOnClickListener(this);
        timeIv.setOnClickListener(this);
        bookLl.setOnClickListener(this);
        noteBook.setOnClickListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//抽屉关闭手势滑动
        editorView = byView(R.id.notes_text_content_et);
//        EventBus.getDefault().register(this);//注册Eventbus
//
        btnStart = byView(R.id.recording_btn_start);
        recordChronometer = byView(R.id.recoding_chronometer);

    }

    @Override
    protected void initDatas() {
        editTextContent = editorView.getText().toString();
        rootLl.setAnimation(new ViewAnimation(rootLl.getWidth(), rootLl.getHeight(), 1000));
        intent = getIntent();
        if (intent != null) {
            int index = intent.getIntExtra("index", 100);
            if (index == 2) {
                noteTitle.setHint("录音位于" + getCityText());
                recordChronometer.setVisibility(View.VISIBLE);
                doneIv.setImageResource(R.mipmap.btn_start_recoding);
                getOpen();
            } else {
                textView.setVisibility(View.GONE);
                doneIv.setImageResource(R.drawable.item_text_notes_title_done);
            }
            setEditImg();
            Bundle bundleSave = getIntent().getExtras();
            if (bundleSave != null) {
                SaveBean saveBean = (SaveBean) bundleSave.getSerializable("saveBean");
                if (saveBean != null) {
                    String title = saveBean.getTitle();
                    String allContent = saveBean.getAllContent();
                    String noteName = saveBean.getNoteName();
                    if (title != null) {
                        noteTitle.setText(title);
                    }
                    if (allContent != null) {
                        List<String> list = new ArrayList<>();
                        list.add(allContent);
                        editorView.setmContentList(list);
                    }
                    if (noteName != null) {
                        notesBookName.setText(noteName);
                    }
                }
            }
        }
    }

    public static void setText(String string) {
        nameTv.setText(string);
    }

    private void setEditImg() {
        PhotoBean bean = (PhotoBean) intent.getSerializableExtra("photo");
        if (bean != null) {
            noteTitle.setHint("快照位于" + getCityText());
            Bitmap bitmap = getBitmap(bean.getBitmap());
            saveCroppedImage(bitmap);
            if (file != null) {
                editorView.insertBitmap(file.getPath());
            }
            imgCurse.add(new int[]{editorView.getIndex(), editorView.getPicture()});

        }
        String photoUrl = intent.getStringExtra("photoUrl");
        if (photoUrl != null) {
            noteTitle.setHint("文件");
            if (photoUrl.endsWith(".png") || photoUrl.endsWith(".jpg")) {
                editorView.insertBitmap(photoUrl);
                Bitmap bitmap = editorView.getSmallBitmap(photoUrl);
                bitmapList = MainActivity.getBytes(bitmap);
                imgCurse.add(new int[]{editorView.getIndex(), editorView.getPicture()});
            }

            String content = intent.getStringExtra("text");
            if (content != null) {
                editorView.setText(content);
            }
        }
        setIfTitles(1);
        setSpeaking(editTextContent);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String string = bundle.getString("key");
            if (string != null && string.equals("1")) {
                goTo(TextNotesActivity.this, PenThinActivity.class);
            }
        }
    }

    private void getOpen() {
        doneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart == false) {
                    startRecord();
                    doneIv.setImageResource(R.mipmap.btn_stop_recoding);
                    Toast.makeText(TextNotesActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
                    recordChronometer.setBase(SystemClock.elapsedRealtime() - recordingTime);// 跳过已经记录了的时间，起到继续计时的作用
                    recordChronometer.start();
                    isStart = true;
                } else {
                    showProgressDilog();
                    Toast.makeText(TextNotesActivity.this, "结束录音,并保存到本地", Toast.LENGTH_SHORT).show();
                    stopRecord();
                    doneIv.setImageResource(R.mipmap.btn_start_recoding);
                    recordChronometer.stop();
                    recordChronometer.setBase(SystemClock.elapsedRealtime());
                    isStart = false;
                }
            }
        });


    }

    private void stopRecord() {
        if (mr == null) {
            File dir = new File(Environment.getExternalStorageDirectory(), "/sdcards/recodrsongs");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File soundFile = new File(dir, System.currentTimeMillis() + ".amr");
            if (!soundFile.exists()) {
                try {
                    soundFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);  //音频输入源
            mr.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);   //设置输出格式
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);   //设置编码格式
            mr.setOutputFile(soundFile.getAbsolutePath());
            try {
                mr.prepare();
                mr.start();  //开始录制
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void showProgressDilog() {
        progressDialog = new ProgressDialog(TextNotesActivity.this);
        // 设置对话框的图标
        // 设置文字
        progressDialog.setMessage("加载中....");
        // 显示加载进度对话框
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Log.d("aaaa", "sd");
                    Thread.sleep(2000);
                    progressDialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void startRecord() {
        if (mr != null) {
            mr.stop();
            mr.release();
            mr = null;
        }
    }

    private void setSpeaking(String editTextContent) {
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
        if (pictureIndex > 0) {
            String picture = editTextContent.substring(index, pictureIndex);
            mTts.startSpeaking(editTextContent.replace("", picture), mSynListener);
        } else {
            mTts.startSpeaking(editTextContent, mSynListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        index = editorView.getIndex();
        pictureIndex = editorView.getPicture();
        mBehavior = BottomSheetBehavior.from(attachLayout);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        setAttachDrawer();
    }

    private void setAttachDrawer() {
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
        listView.setOnItemClickListener(this);
    }

    /**
     * Eventbus
     *
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(PhotoBean bean) {
        noteTitle.setHint("手写位于" + getCityText());
        Bitmap bitmap = getBitmap(bean.getBitmap());
        Log.d("xxx", "bean.getBitmap():" + bean.getBitmap());

        saveCroppedImage(bitmap);
        Log.d("ddd", file.getPath()+"");
        editorView.insertBitmap(file.getPath());
        imgCurse.add(new int[]{editorView.getIndex(), editorView.getPicture()});
    }

    public Bitmap getBitmap(byte[] data) {
        bitmapList = data;
        return BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
    }

    @Override
    protected void onClickDrawer() {

    }

    @Override
    protected void onClickRight(View v) {

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
        editTextContent = editorView.getText().toString();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        span = new SpannableString(editTextContent);
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
            case R.id.item_formatting_popup_window_bold_iv://加粗
                if (!isClick) {
                    boldIv.setSelected(true);
                    span.setSpan(new StyleSpan(Typeface.BOLD), 0, editorView.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    setEdText();
                    isClick = true;
                } else if (isClick) {
                    boldIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_formatting_popup_window_italic_iv://斜体
                setSpeaking(editorView.getText().toString());
                if (!isClick) {
                    italicIv.setSelected(true);
                    span.setSpan(new StyleSpan(Typeface.ITALIC), 0, editorView.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    setEdText();
                    isClick = true;
                } else if (isClick) {
                    italicIv.setSelected(false);
                    isClick = false;

                }
                break;
            case R.id.item_formatting_popup_window_underline_iv://下划线
                if (!isClick) {
                    underlineIv.setSelected(true);
                    span.setSpan(new UnderlineSpan(), 0, editorView.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    setEdText();
                    isClick = true;

                } else if (isClick) {
                    underlineIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_formatting_popup_window_line_through_iv://中华线(删除)
                if (!isClick) {
                    lineThroughIv.setSelected(true);
                    span.setSpan(new StrikethroughSpan(), 0, editorView.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    setEdText();
                    isClick = true;
                    setEdText();
                } else if (isClick) {
                    lineThroughIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_formatting_popup_window_background_iv://添加背景
                if (!isClick) {
                    backgroundIv.setSelected(true);
                    span.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, editorView.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    setEdText();
                    isClick = true;

                } else if (isClick) {
                    backgroundIv.setSelected(false);
                    isClick = false;

                }
                break;
            case R.id.item_formatting_popup_window_checkbox_iv://复选框


                break;
            case R.id.item_formatting_popup_window_orderly_iv://有序排列
                if (!isClick) {
                    orderlyIv.setSelected(true);
                    isClick = true;


                } else if (isClick) {
                    orderlyIv.setSelected(false);
                    isClick = false;

                }
                break;
            case R.id.item_formatting_popup_window_disorder_iv://无序排列
                if (!isClick) {
                    disorderIv.setSelected(true);
                    isClick = true;

                } else if (isClick) {
                    disorderIv.setSelected(false);
                    isClick = false;

                }
                break;
            case R.id.item_formatting_popup_window_left_iv://左侧缩进
                if (!isClick) {
                    leftIv.setSelected(true);
                    isClick = true;

                } else if (isClick) {
                    leftIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_formatting_popup_window_right_iv://右侧缩进
                if (!isClick) {
                    rightIv.setSelected(true);
                    isClick = true;
                } else if (isClick) {
                    rightIv.setSelected(false);
                    isClick = false;
                }
                break;
            case R.id.item_text_notes_done_iv://完成返回
                if (!isPopupClick) {
                    doneIv.setSelected(true);
                    isPopupClick = true;
                } else if (isPopupClick) {
                    doneIv.setSelected(false);
                    isPopupClick = false;
                    if (editTextContent.isEmpty()) {
                        finish();
                    } else {
                        editTextContent = editorView.getText().toString();
                        setDialog();
                    }
                }
                break;
            case R.id.item_text_notes_left_undo_iv://撤销
                if (!isPopupClick) {
                    underIv.setSelected(true);
                    isPopupClick = true;
                } else if (isPopupClick) {
                    underIv.setSelected(false);
                    isPopupClick = false;
                }
                break;
            case R.id.item_text_notes_right_redo_iv://撤销
                if (!isPopupClick) {
                    redoIv.setSelected(true);
                    isPopupClick = true;
                } else if (isPopupClick) {
                    redoIv.setSelected(false);
                    isPopupClick = false;
                }
                break;
            case R.id.item_text_notes_attach_iv://附件
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
            case R.id.item_text_notes_pen_thin_img://画笔
                goTo(TextNotesActivity.this, PenThinActivity.class);
                break;
            case R.id.item_text_notes_share_iv://分享
                break;
            case R.id.notes_text_time_img://时钟
                Intent intent = new Intent(TextNotesActivity.this, RemendPopActivity.class);
                startActivity(intent);
                break;
            case R.id.notes_text_book_layout://第一个笔记本
                Intent noteIntent = new Intent(TextNotesActivity.this, NoteBookActivity.class);
                startActivity(noteIntent);
                break;

        }

    }

    private void setEdText() {
        if (editorView.length() > 0) {
            editorView.setText(span);
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
        EventBus.getDefault().unregister(this);//解除注册

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
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
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

    /**
     * listView的行点击事件
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intentActtchenment = new Intent(Intent.ACTION_GET_CONTENT);
        switch (i) {
            case 0:
                intentActtchenment.setType("image/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intentActtchenment.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intentActtchenment, 0);
                break;
            case 1:
                intentActtchenment.setType("image/*");
                intentActtchenment.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intentActtchenment, 1);
                break;
            case 2:
                intentActtchenment.setType("video/*");
                intentActtchenment.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intentActtchenment, 1);
                break;
            case 3:
                intentActtchenment.setType("audio/media/*");
                intentActtchenment.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intentActtchenment, 1);
                break;
            case 4:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 4);
                break;
            case 5:

                break;
            case 6:
                goTo(TextNotesActivity.this, PenThinActivity.class);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        editTextContent = editorView.getText().toString();

        if (keyCode == KeyEvent.KEYCODE_BACK && !editTextContent.isEmpty()) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000 && (!editTextContent.isEmpty())) {
            setDialog();
            exitTime = System.currentTimeMillis();
        }
    }

    private void setDialog() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM:dd");
        Date nowTime = new Date(System.currentTimeMillis());
        format = sdf.format(nowTime);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否保存?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<String> stringList = new ArrayList<>();
                        String content = editTextContent;
                        for (int i = 0; i < imgCurse.size(); i++) {
                            String img = content.substring(imgCurse.get(i)[0], imgCurse.get(i)[1]);
                            stringList.add(img);
                        }
                        for (int i = 0; i < stringList.size(); i++) {
                            content = content.replace(stringList.get(i), "");
                        }
                        SaveBean saveBean = new SaveBean();
                        saveBean.setNoteName(notesBookName.getText().toString());
                        saveBean.setContent(content);
                        saveBean.setBitmap(bitmapList);
                        saveBean.setDate(format);
                        saveBean.setAllContent(editTextContent);
                        if (noteTitle.getText().toString().isEmpty()) {
                            saveBean.setTitle(noteTitle.getHint().toString());
                        } else {
                            saveBean.setTitle(noteTitle.getText().toString());
                        }
                        LiteOrmInstance.getLiteOrmInstance().insert(saveBean);

                        finish();
                    }
                }

        );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }

        );
        builder.create().show();
    }


}
