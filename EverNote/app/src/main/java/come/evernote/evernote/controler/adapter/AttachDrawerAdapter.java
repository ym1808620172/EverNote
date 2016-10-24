package come.evernote.evernote.controler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.AttachDrawerBean;

/**
 * Created by dllo on 16/10/22.
 * TextNotesActivity界面附件的抽屉的listView的适配器
 */
public class AttachDrawerAdapter extends BaseAdapter {
    private Context context;
    private List<AttachDrawerBean> data;

    public AttachDrawerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AttachDrawerBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_attach_drawer_listview, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(data.get(i).getText());
        holder.imageView.setImageResource(data.get(i).getImage());
        return view;
    }

    class ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.attach_drawer_list_iv);
            textView = (TextView) view.findViewById(R.id.attach_drawer_list_tv);
        }
    }
}
