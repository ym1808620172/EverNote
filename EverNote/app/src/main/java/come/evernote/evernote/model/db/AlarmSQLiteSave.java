package come.evernote.evernote.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import come.evernote.evernote.model.bean.AlarmData;

/**
 * Description:
 * <br>Created on 15-8-14.
 * <br>Email: dyh920827@gmail.com
 * <br>Website: <a href="http://www.kyletung.com">Kyle Tung</a>
 *
 * @author Kyle Tung
 * @version 0.1.2
 */
public class AlarmSQLiteSave {

    private SQLiteDatabase db;
    public static final String CREATE_ALARM_SQL = "create table Alarm (" +
            "_id integer primary key autoincrement," +
            "year integer," +
            "month integer," +
            "day integer," +
            "hour integer," +
            "minute integer," +
            "alarmSwitch integer default 1)";

    //init SQLite database
    public AlarmSQLiteSave(Context context) {
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(context, "Alarm.db3", null, 1);
        db = mySQLiteHelper.getReadableDatabase();
    }

    //init adapter
    public List<AlarmData> initAlarm() {
        List<AlarmData> list = new ArrayList<>();
        Cursor cursor = db.query("Alarm", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Calendar calendarGet = Calendar.getInstance();
            Calendar calenderSet = Calendar.getInstance();
            calenderSet.set(cursor.getInt(cursor.getColumnIndex("year")), cursor.getInt(cursor.getColumnIndex("month")), cursor.getInt(cursor.getColumnIndex("day")), cursor.getInt(cursor.getColumnIndex("hour")), cursor.getInt(cursor.getColumnIndex("minute")), 0);
            if (calenderSet.getTimeInMillis() > calendarGet.getTimeInMillis()) {
                AlarmData alarmData = new AlarmData(cursor.getInt(cursor.getColumnIndex("year")), cursor.getInt(cursor.getColumnIndex("month")), cursor.getInt(cursor.getColumnIndex("day")), cursor.getInt(cursor.getColumnIndex("hour")), cursor.getInt(cursor.getColumnIndex("minute")), cursor.getInt(cursor.getColumnIndex("alarmSwitch")));
                list.add(alarmData);
            } else {
                AlarmData alarmData = new AlarmData(cursor.getInt(cursor.getColumnIndex("year")), cursor.getInt(cursor.getColumnIndex("month")), cursor.getInt(cursor.getColumnIndex("day")), cursor.getInt(cursor.getColumnIndex("hour")), cursor.getInt(cursor.getColumnIndex("minute")), 0);
                list.add(alarmData);
            }
        }
        cursor.close();
        return list;
    }

    //insert an alarm settings to SQLite database
    public void saveAlarm(AlarmData alarmData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("year", alarmData.getYear());
        contentValues.put("month", alarmData.getMonth());
        contentValues.put("day", alarmData.getDay());
        contentValues.put("hour", alarmData.getHour());
        contentValues.put("minute", alarmData.getMinute());
        contentValues.put("alarmSwitch", alarmData.getAlarmSwitch());
        db.insert("Alarm", "year", contentValues);
    }

    //delete a record from SQLite database
    public void deleteAlarm(AlarmData alarmData) {
        try {
        db.execSQL("delete from Alarm where year = " + alarmData.getYear() + " and month = " + alarmData.getMonth() + " and day = " + alarmData.getDay() + " and hour = " + alarmData.getHour() + " and minute = " + alarmData.getMinute());
            System.out.println("delete SQLite success");
        } catch (Exception e) {
            System.out.println("delete SQLite failed");
        }
    }

    //change the switch value
    public void updateAlarm(AlarmData alarmData, int i) {
        if (i == 1) {
            db.execSQL("update Alarm set alarmSwitch = 1 where year = " + alarmData.getYear() + " and month = " + alarmData.getMonth() + " and day = " + alarmData.getDay() + " and hour = " + alarmData.getHour() + " and minute = " + alarmData.getMinute());
            System.out.println(">>> update SQLite success <<<");
        } else {
            db.execSQL("update Alarm set alarmSwitch = 0 where year = " + alarmData.getYear() + " and month = " + alarmData.getMonth() + " and day = " + alarmData.getDay() + " and hour = " + alarmData.getHour() + " and minute = " + alarmData.getMinute());
            System.out.println(">>> update SQLite success <<<");
        }
    }

    class MySQLiteHelper extends SQLiteOpenHelper {

        public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ALARM_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
