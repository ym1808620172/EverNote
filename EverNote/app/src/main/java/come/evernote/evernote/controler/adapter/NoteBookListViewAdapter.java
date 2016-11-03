package come.evernote.evernote.controler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.model.bean.NoteBookListViewBean;

/**
 * Created by dllo on 16/10/31.
 */
public class NoteBookListViewAdapter extends BaseAdapter {
    private Context context;
    private List<NoteBookListViewBean> data;

    public NoteBookListViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NoteBookListViewBean> data) {
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
    public void addOneData(NoteBookListViewBean bean){
        data.add(bean);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_note_book_list_view, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        NoteBookListViewBean bean = data.get(i);
        holder.textView.setText(bean.getText());
        return view;
    }

    class ViewHolder {
        private TextView textView;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.item_note_book_list_view_tv);
        }
    }
}
