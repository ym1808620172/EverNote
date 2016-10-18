package com.du.baseadapterdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/10/18.
 */
public abstract class AbsBaseAdapter<D ,VH extends AbsBaseAdapter.BaseHolder> extends BaseAdapter {
    protected List<D> mDatas;
    protected Context context;

    public AbsBaseAdapter(Context context) {
        this.context = context;
    }

    public void setmDatas(List<D> list) {
        if (mDatas == null){
            mDatas = new ArrayList<>();
        }
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public D getItem(int i) {
        return mDatas == null ? null : mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH vh = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(setItemLayout(),viewGroup,false);
            vh = onCreateViewHolder(view);
            view.setTag(vh);
        }else {
            vh = (VH) view.getTag();
        }
        D itemData = getItem(i);
        //设置
        onBindViewHolder(vh,itemData,i);
        return view;
    }

    /**
     * 设置数据
     * @param vh holder
     * @param itemData 实体类
     * @param i 位置
     */
    protected abstract void onBindViewHolder(VH vh, D itemData, int i);

    /**
     * 绑定布局
     * @return R.layout.xxx
     */
    protected abstract int setItemLayout();

    /**
     * 初始化ViewHolder
     * @param view 布局文件
     * @return
     */
    protected abstract VH onCreateViewHolder(View view);

    protected static class BaseHolder{
        View itemView;
        public BaseHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}
