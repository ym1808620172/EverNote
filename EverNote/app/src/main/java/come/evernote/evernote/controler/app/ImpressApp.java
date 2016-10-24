package come.evernote.evernote.controler.app;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import come.evernote.evernote.R;

/**
 * Created by dllo on 16/10/17.
 * APP
 */
public class ImpressApp extends Application {
    private  static Context context;
    @Override
    public void onCreate() {
        SpeechUtility.createUtility(ImpressApp.this, SpeechConstant.APPID + "=580b3054");
        super.onCreate();
        context = getApplicationContext();
    }
    public  static Context getContext(){return  context;}
}
