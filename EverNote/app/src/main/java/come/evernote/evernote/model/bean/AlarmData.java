package come.evernote.evernote.model.bean;

/**
 * Description:
 * <br>Created on 15-8-14.
 * <br>Email: dyh920827@gmail.com
 * <br>Website: <a href="http://www.kyletung.com">Kyle Tung</a>
 *
 * @author Kyle Tung
 * @version 0.1
 */
public class AlarmData {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int alarmSwitch;


    public AlarmData() {
        year = 0;
        month = 0;
        day = 0;
        hour = 0;
        minute = 0;
        alarmSwitch = 1;
    }

    public AlarmData(int year, int month, int day, int hour, int minute, int alarmSwitch) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.alarmSwitch = alarmSwitch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getAlarmSwitch() {
        return alarmSwitch;
    }

    public void setAlarmSwitch(int alarmSwitch) {
        this.alarmSwitch = alarmSwitch;
    }
}
