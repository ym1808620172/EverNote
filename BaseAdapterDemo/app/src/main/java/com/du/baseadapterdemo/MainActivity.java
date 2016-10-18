package com.du.baseadapterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.main_list);
        TitleAdapter adapter = new TitleAdapter(this);
        List<MyBean> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add(new MyBean("测试" + i));

        }
        adapter.setmDatas(data);
        listView.setAdapter(adapter);
    }
}
