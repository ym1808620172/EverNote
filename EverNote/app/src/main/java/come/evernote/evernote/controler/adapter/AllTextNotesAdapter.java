package come.evernote.evernote.controler.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.SaveBean;

/**
 * Created by dllo on 16/10/29.
 *
 */
public class AllTextNotesAdapter extends BaseAdapter {
    private List<SaveBean> been;
    private Context context;

    public AllTextNotesAdapter(Context context) {
        this.context = context;
    }

    public void setBeen(List<SaveBean> been) {
        this.been = been;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_all_text_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SaveBean saveBeen = been.get(position);

        if (saveBeen != null) {
            if (saveBeen.getTitle() != null) {
                viewHolder.titleTv.setText(saveBeen.getTitle());
            }
            if (!saveBeen.getDate().isEmpty()) {
                viewHolder.timeTv.setText(saveBeen.getDate());
            }
            if (!saveBeen.getContent().isEmpty()) {
                viewHolder.contentTv.setText(saveBeen.getContent());
            }
            byte[] list = saveBeen.getBitmap();
            if (list != null) {
                viewHolder.img.setImageBitmap(getBitmap(list));
            }
        }
        return convertView;
    }

    class ViewHolder {

        private final TextView titleTv;
        private final ImageView img;
        private final TextView timeTv;
        private final TextView contentTv;

        public ViewHolder(View view) {
            titleTv = (TextView) view.findViewById(R.id.item_all_text_title_tv);
            img = (ImageView) view.findViewById(R.id.item_all_text_img);
            timeTv = (TextView) view.findViewById(R.id.item_all_text_time_tv);
            contentTv = (TextView) view.findViewById(R.id.item_all_text_content_tv);
        }
    }

    public Bitmap getBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
    }
}
