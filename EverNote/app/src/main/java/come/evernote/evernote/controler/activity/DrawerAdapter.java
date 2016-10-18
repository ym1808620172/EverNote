package come.evernote.evernote.controler.activity;

import android.content.Context;
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
 */
public class DrawerAdapter extends BaseAdapter {
    private List<DrawerShowBean> datas;
    private Context context;

    public DrawerAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<DrawerShowBean> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 :datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? null:datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_drawer,parent,false);
            holder = new DrawerViewHolder(convertView);
            convertView.setTag(holder);

        }else {
            holder = (DrawerViewHolder) convertView.getTag();
        }

        holder.textView.setText(datas.get(position).getContent());
        holder.img.setImageResource(datas.get(position).getImg());
        return convertView;
    }

    class  DrawerViewHolder{
        TextView textView;
        ImageView img;
        public  DrawerViewHolder(View view){
            textView = (TextView) view.findViewById(R.id.item_drawer_tv);
            img = (ImageView)view.findViewById(R.id.item_drawer_img);
        }
    }
}
