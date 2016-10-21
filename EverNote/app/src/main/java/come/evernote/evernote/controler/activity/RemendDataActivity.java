package come.evernote.evernote.controler.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import org.greenrobot.eventbus.EventBus;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.ClockSizeBean;
import come.evernote.evernote.model.db.G;
import come.evernote.evernote.utils.ISetClockSize;
import come.evernote.evernote.view.ClockView;

public class RemendDataActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    private RadioButton radioButton;
    private LinearLayout dayLl;
    private LinearLayout timeLl;
    private LinearLayout rootLl;
    private ISetClockSize clockSize;
    private ClockView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remend_data_acticity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        radioButton = (RadioButton) findViewById(R.id.remend_data_radio_btn);
        dayLl = (LinearLayout) findViewById(R.id.remend_data_day);
        timeLl = (LinearLayout) findViewById(R.id.remend_data_time);
        rootLl = (LinearLayout) findViewById(R.id.data_activity_root);
        clockView = (ClockView) findViewById(R.id.remend_data_clock);
        radioButton.setOnCheckedChangeListener(this);
        radioButton.setChecked(true);
        clockSize =  clockView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (dayLl.getHeight()!=0){
            clockSize.OnSetClockSize(dayLl.getWidth(),dayLl.getHeight());
        }
        if (timeLl.getHeight() != 0) {
            ClockSizeBean bean = new ClockSizeBean();
            bean.setWidth(timeLl.getWidth());
            bean.setHeight(timeLl.getHeight());

        }
        if (isChecked) {
            dayLl.setVisibility(View.VISIBLE);
            timeLl.setVisibility(View.GONE);
        } else {
            dayLl.setVisibility(View.GONE);
            timeLl.setVisibility(View.VISIBLE);
        }
    }
}
