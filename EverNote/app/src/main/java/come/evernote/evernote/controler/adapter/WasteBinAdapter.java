package come.evernote.evernote.controler.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.WasteBinBean;

/**
 * Created by dllo on 16/10/29.
 */
public class WasteBinAdapter extends BaseAdapter {
    private List<WasteBinBean> been;
    private Context context;
    private ViewHolder viewHolder;
    private HashMap<Integer, Boolean> map;
    private boolean isShow = false;

    public WasteBinAdapter(Context context) {
        this.context = context;

    }

    public void setShow(boolean show) {
        isShow = show;
        notifyDataSetChanged();
    }

    public void setBeen(List<WasteBinBean> been) {
        this.been = been;
        map = new HashMap<>();
        for (int i = 0; i < been.size(); i++) {
            map.put(i, false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return been != null && been.size() > 0 ? been.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return been != null && been.size() > 0 ? been.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_all_text_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        WasteBinBean saveBeen = been.get(position);
        if (saveBeen != null) {
            if (saveBeen.getTitle() != null) {
                viewHolder.titleTv.setText(saveBeen.getTitle());
            }
            if (saveBeen.getDate() != null) {
                viewHolder.timeTv.setText(saveBeen.getDate());
            }
            if (saveBeen.getContent() != null) {
                viewHolder.contentTv.setText(saveBeen.getContent());
            }
            byte[] list = saveBeen.getBitmap();
            if (list != null) {
                viewHolder.img.setImageBitmap(getBitmap(list));
            }
            if (map.get(position)) {
                viewHolder.checkBox.setChecked(true);
                Log.d("ddd", "viewHolder.checkBox.isChecked():" + viewHolder.checkBox.isChecked());
            } else {
                viewHolder.checkBox.setChecked(false);
            }
        }
        if (isShow){
            viewHolder.checkBox.setVisibility(View.VISIBLE);
        }else {
            viewHolder.checkBox.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {

        private final TextView titleTv;
        private final ImageView img;
        private final TextView timeTv;
        private final TextView contentTv;
        private final CheckBox checkBox;

        public ViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_all_text_title_tv);
            img = (ImageView) view.findViewById(R.id.item_all_text_img);
            timeTv = (TextView) view.findViewById(R.id.item_all_text_time_tv);
            contentTv = (TextView) view.findViewById(R.id.item_all_text_content_tv);
            checkBox = (CheckBox) view.findViewById(R.id.item_text_list_box);
        }
    }

    public Bitmap getBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
    }

    public void setChickBox(int position) {
        map.put(position, true);
        notifyDataSetChanged();
    }
}
