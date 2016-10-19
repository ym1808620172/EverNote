package come.evernote.evernote.controler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/10/18.
 * BaseAdapter的基类,用于ListView
 */
public abstract class AbsBaseAdapter<T, VH extends AbsBaseAdapter.BaseHolder> extends BaseAdapter {
    protected List<T> mDatas;
    protected Context context;

    public AbsBaseAdapter(Context context) {
        this.context = context;
    }

    public void setmDatas(List<T> newList) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(setItemLayout(), parent, false);
            vh = onCreateViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (VH) convertView.getTag();
        }
        //获得行布局数据
        T itemData = getItem(position);
        //设置
        onBindViewHolder(vh, itemData, position);

        return convertView;
    }

    protected abstract int setItemLayout();

    protected abstract VH onCreateViewHolder(View convertView);

    protected abstract void onBindViewHolder(VH vh, T itemData, int position);

    protected static class BaseHolder {
        View itemView;

        public BaseHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}
