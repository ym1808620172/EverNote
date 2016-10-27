package come.evernote.evernote.controler.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.alarm.AlarmManager;
import come.evernote.evernote.model.bean.AlarmData;
import come.evernote.evernote.model.db.AlarmSQLiteSave;

public class RemendDataActivity extends Activity {
    private AlarmData alarmData;
    private Calendar calendar;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    private AlarmManager alarmMyManager;
    private AlarmSQLiteSave alarmSQLiteSave;
    private List<AlarmData> alarmDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remend_data_acticity);
        alarmDatas = new ArrayList<>();
        alarmData = new AlarmData();
        calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                alarmData.setYear(year);
                alarmData.setMonth(monthOfYear);
                alarmData.setDay(dayOfMonth);
                timePickerDialog = new TimePickerDialog(RemendDataActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        alarmData.setHour(hourOfDay);
                        alarmData.setMinute(minute);
                        addAlarm(alarmData);
                        alarmDatas.add(alarmData);
                        finish();
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
                timePickerDialog.setCanceledOnTouchOutside(false);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        datePickerDialog.setCanceledOnTouchOutside(false);

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
    }

    public void addAlarm(AlarmData alarmData) {
        alarmMyManager = new AlarmManager(this, alarmDatas);
        alarmSQLiteSave = new AlarmSQLiteSave(this);
        Calendar calendarGet = Calendar.getInstance();
        Calendar calendarSet = Calendar.getInstance();
        calendarSet.set(alarmData.getYear(), alarmData.getMonth(), alarmData.getDay(), alarmData.getHour(), alarmData.getMinute(), 0);
        if (calendarGet.getTimeInMillis() < calendarSet.getTimeInMillis() & alarmData.getYear() < 2100) {
            alarmSQLiteSave.saveAlarm(alarmData);
            alarmMyManager.addAlarmManager(alarmData);
        } else {
            Toast.makeText(this, "少年，无法时空穿越", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
