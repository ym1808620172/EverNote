package come.evernote.evernote.controler.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Description:
 * <br>Created on 15-8-16.
 * <br>Email: dyh920827@gmail.com
 * <br>Website: <a href="http://www.kyletung.com">Kyle Tung</a>
 *
 * @author Kyle Tung
 * @version 0.1.2
 */
public class BootInitAlarmReceiver extends BroadcastReceiver {

    public BootInitAlarmReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, AlarmBootService.class));
    }
}
