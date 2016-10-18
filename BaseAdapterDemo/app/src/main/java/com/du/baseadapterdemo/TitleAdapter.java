package com.du.baseadapterdemo;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dllo on 16/10/18.
 */
public class TitleAdapter extends AbsBaseAdapter <MyBean,TitleAdapter.MyHolder>{
    public TitleAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(MyHolder myHolder, MyBean itemData, int i) {
        myHolder.textView.setText(itemData.getTitle());
    }

    @Override
    protected int setItemLayout() {
        return R.layout.item_list;
    }

    @Override
    protected MyHolder onCreateViewHolder(View view) {
        return new MyHolder(view);
    }
    public class MyHolder extends AbsBaseAdapter.BaseHolder{
    private TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
