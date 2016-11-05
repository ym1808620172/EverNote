package come.evernote.evernote.controler.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.NoteBookListViewBean;

/**
 * Created by dllo on 16/11/2.
 */
public class NewNotesBookActivity extends Activity implements View.OnClickListener {
    private EditText editText;
    private TextView okTv;
    private TextView cancelTv;
    private EventBus eventbus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_notes_new);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        editText = (EditText) findViewById(R.id.note_book_et);
        okTv = (TextView) findViewById(R.id.note_book_ok_tv);
        cancelTv = (TextView) findViewById(R.id.note_book_cancel_tv);
        okTv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
        eventbus = EventBus.getDefault();//初始化
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.note_book_cancel_tv:
                finish();
                break;
            case R.id.note_book_ok_tv:
                String string = editText.getText().toString();
                NoteBookListViewBean bookBean = new NoteBookListViewBean();
                bookBean.setText(string);
                eventbus.post(bookBean);
                Intent intent = new Intent();
                setResult(300,intent);
                finish();
                break;
        }
    }
}
