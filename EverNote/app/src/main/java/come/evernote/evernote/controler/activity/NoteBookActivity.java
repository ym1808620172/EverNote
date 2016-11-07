package come.evernote.evernote.controler.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.adapter.NoteBookListViewAdapter;
import come.evernote.evernote.model.bean.NoteBookListViewBean;
import come.evernote.evernote.model.bean.SaveBean;
import come.evernote.evernote.model.db.LiteOrmInstance;

/**
 * Created by dllo on 16/10/31.
 * 选择笔记页面(点击我的第一个笔记本弹出)
 */
public class NoteBookActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView listView;
    private NoteBookListViewAdapter adapter;
    private ImageView bookIv;
    private boolean isHave = false;
    private boolean SpisHave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_note);
        listView = (ListView) findViewById(R.id.note_book_list_view);
        bookIv = (ImageView) findViewById(R.id.note_book_iv);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initData();
    }

    private void initData() {
        adapter = new NoteBookListViewAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        bookIv.setOnClickListener(this);
        SharedPreferences sp = getSharedPreferences("name",MODE_PRIVATE);
        SpisHave = sp.getBoolean("ishave",false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<NoteBookListViewBean> listViewBeen =LiteOrmInstance.getLiteOrmInstance().queryAll(NoteBookListViewBean.class);
        if (!SpisHave){
            for (int i = 0; i < listViewBeen.size(); i++) {
                if (listViewBeen.get(i).getNoteName().equals("我是第一个笔记本")) {
                    isHave = true;
                }
            }
            if (!isHave){
                NoteBookListViewBean bean = new NoteBookListViewBean();
                bean.setNoteName("我是第一个笔记本");
                LiteOrmInstance.getLiteOrmInstance().insert(bean);
            }
        }
        adapter.setData(listViewBeen);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        NoteBookListViewBean bean = (NoteBookListViewBean) adapterView.getItemAtPosition(i);
        String text = bean.getNoteName();
        TextNotesActivity.setText(text);
        finish();
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(NoteBookActivity.this,NewNotesBookActivity.class);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==300){
            finish();
        }
    }

}
