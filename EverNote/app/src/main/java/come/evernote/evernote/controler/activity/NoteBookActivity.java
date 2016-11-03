package come.evernote.evernote.controler.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.adapter.NoteBookListViewAdapter;
import come.evernote.evernote.model.bean.NoteBookListViewBean;

/**
 * Created by dllo on 16/10/31.
 * 选择笔记页面(点击我的第一个笔记本弹出)
 */
public class NoteBookActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private LinearLayout noteBookLayout;
    private ListView listView;
    private NoteBookListViewAdapter adapter;
    private List<NoteBookListViewBean> data;
    private ImageView bookIv;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_note);
        noteBookLayout = (LinearLayout) findViewById(R.id.note_book_layout);
        listView = (ListView) findViewById(R.id.note_book_list_view);
        bookIv = (ImageView) findViewById(R.id.note_book_iv);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initData();
    }

    private void initData() {
        data = new ArrayList<>();
        adapter = new NoteBookListViewAdapter(this);

        data.add(new NoteBookListViewBean("我的第一个笔记本"));
        adapter.setData(data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        bookIv.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        noteBookLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(this, "你点击了" + i + "行", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(NoteBookActivity.this,NewNotesBookActivity.class);
        startActivity(intent);
    }
    public static void addList(String string){
    }

}
