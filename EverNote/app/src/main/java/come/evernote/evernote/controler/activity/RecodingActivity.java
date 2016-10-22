package come.evernote.evernote.controler.activity;

import android.app.ProgressDialog;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import come.evernote.evernote.R;

/**
 * Created by dllo on 16/10/21.
 * 录音详情界面
 * 将录音保存在本地
 */
public class RecodingActivity  extends  AbsBaseActivity{

    private ImageView btnStart;
    private Chronometer recordChronometer;
    private boolean isStart = false;
    private MediaRecorder mr = null;
    private long recordingTime = 0;// 记录下来的时间
    // 进度对话框
    private ProgressDialog progressDialog;

    @Override
    protected int setLayout() {
        return R.layout.activity_recoding;
    }

    @Override
    protected void initView() {
        btnStart = byView(R.id.recording_btn_start);
      //  btnStop = byView(R.id.recording_btn_stop);
        recordChronometer =byView(R.id.recoding_chronometer);

    }

    @Override
    protected void initDatas() {
        setIfTitles(1);
        getOpen();


    }


    private void getOpen() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isStart==false) {
                    startRecord();
                    btnStart.setImageResource(R.mipmap.btn_stop_recording);
                    Toast.makeText(RecodingActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
                    recordChronometer.setBase(SystemClock.elapsedRealtime() - recordingTime);// 跳过已经记录了的时间，起到继续计时的作用
                    recordChronometer.start();
                    isStart = true;
                } else {
                    showProgressDilog();
                    stopRecord();
                    btnStart.setImageResource(R.mipmap.btn_start_recoding);
                    Toast.makeText(RecodingActivity.this, "结束录音,并保存到本地", Toast.LENGTH_SHORT).show();
                    recordChronometer.stop();
                    recordChronometer.setBase(SystemClock.elapsedRealtime());
                    isStart = false;
                }
            }
        });
    }

    private void showProgressDilog() {
        progressDialog = new ProgressDialog(RecodingActivity.this);
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


    private void stopRecord() {

        if (mr == null) {
            File dir = new File(Environment.getExternalStorageDirectory(),"/sdcards/recodrsongs");
            Log.d("MainActivity", "Environment.getExternalStorageDirectory():" + Environment.getExternalStorageDirectory());
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

    private void startRecord() {

        if (mr != null) {
            mr.stop();
            mr.release();
            mr = null;
        }
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

    @Override
    public void onClick(View v) {

    }
}
