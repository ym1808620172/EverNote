package come.evernote.evernote.controler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.NoteBookBean;
import come.evernote.evernote.model.bean.SaveBean;

/**
 * Created by dllo on 16/10/31.
 */
public class NotesBookAdapter extends BaseAdapter {
    private List<NoteBookBean> list;
    private Context context;

    public NotesBookAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<NoteBookBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null && list.size() > 0 ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_note_book_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NoteBookBean saveBean = list.get(position);
        if (saveBean != null) {
            viewHolder.nameTv.setText(saveBean.getNoteName());
            viewHolder.sizeTv.setText(saveBean.getSize() + " 条笔记");
        }
        return convertView;
    }

    class ViewHolder {

        private final TextView nameTv;
        private final TextView sizeTv;

        public ViewHolder(View view) {
            nameTv = (TextView) view.findViewById(R.id.notes_book_name_tv);
            sizeTv = (TextView) view.findViewById(R.id.notes_book_size);
        }
    }
}
