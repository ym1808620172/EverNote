package come.evernote.evernote.controler.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import come.evernote.evernote.model.bean.AlarmData;
import come.evernote.evernote.model.db.AlarmSQLiteSave;


/**
 * Description:
 * <br>Created on 15-8-16.
 * <br>Email: dyh920827@gmail.com
 * <br>Website: <a href="http://www.kyletung.com">Kyle Tung</a>
 *
 * @author Kyle Tung
 * @version 0.1.2
 */
public class AlarmBootService extends Service {

    public AlarmBootService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmSQLiteSave alarmSQLiteSave = new AlarmSQLiteSave(getApplicationContext());
        List<AlarmData> list = alarmSQLiteSave.initAlarm();
        new AlarmManager(getApplicationContext(), list);
        return super.onStartCommand(intent, flags, startId);
    }
}
