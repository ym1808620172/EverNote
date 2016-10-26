package come.evernote.evernote.controler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.DrawerShowBean;

/**
 * Created by dllo on 16/10/18.
 * 抽屉界面适配器
 */
public class DrawerAdapter extends BaseAdapter {
    private List<DrawerShowBean> datas;
    private Context context;
    private int index;

    public void setIndex(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

    public DrawerAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<DrawerShowBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerViewHolder holder = null;
        Log.d("aaaa", "positionaaa:" + position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_drawer, parent, false);
            holder = new DrawerViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (DrawerViewHolder) convertView.getTag();
        }
        holder.textView.setText(datas.get(position).getContent());
        holder.img.setImageResource(datas.get(position).getImg());
        Log.d("aaaa", "index:" + index);
        if (position == index) {
            Log.d("DrawerAdapter", "he");
            holder.textView.setTextColor(Color.GREEN);
            if (position == 0){
                holder.img.setImageResource(R.mipmap.article);
            }else if (position == 1){
                holder.img.setImageResource(R.mipmap.greennote);
            }else if (position == 2){
                holder.img.setImageResource(R.mipmap.greenwujiaoxing);
            }else  if (position == 3){
                holder.img.setImageResource(R.mipmap.greenwork);
            }else if (position == 4){
                holder.img.setImageResource(R.mipmap.greenlaji);
            }else  if (position == 5){
                holder.img.setImageResource(R.mipmap.grenntop);
            }else if (position == 6){
                holder.img.setImageResource(R.mipmap.greendiqiu);
            }else  if (position == 7){
                holder.img.setImageResource(R.mipmap.greenseting);
            }

        } else {
            holder.textView.setTextColor(Color.GRAY);
        }
        return convertView;
    }


    class DrawerViewHolder {
        TextView textView;
        ImageView img;

        public DrawerViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.item_drawer_tv);
            img = (ImageView) view.findViewById(R.id.item_drawer_img);
        }
    }
}
