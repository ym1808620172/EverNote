package come.evernote.evernote.controler.alarm;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.WindowManager;

/**
 * Description:
 * <br>Created on 15-8-16.
 * <br>Email: dyh920827@gmail.com
 * <br>Website: <a href="http://www.kyletung.com">Kyle Tung</a>
 *
 * @author Kyle Tung
 * @version 0.1.3
 */
public class AlarmService extends Service {

    public AlarmService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println(">>> create alarm service success <<<");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            System.out.println(">>> alarm time now <<<");
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(getApplicationContext(), alarmUri);
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION) != 0) {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
            AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
            dialog.setTitle("闹铃");
            dialog.setMessage("您所设置的闹铃已经开启");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mediaPlayer.stop();
                    System.out.println(">>> alert dialog show <<<");
                }
            });
            AlertDialog alertDialog = dialog.create();
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alertDialog.show();
        } catch (Exception e) {
            System.out.println(">>> start alarm failed <<<");
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
