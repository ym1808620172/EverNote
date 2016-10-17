package come.evernote.evernote.controler.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by dllo on 16/10/17.
 * APP
 */
public class ImpressApp extends Application {
    private  static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public  static Context getContext(){return  context;}
}
