package come.evernote.evernote.controler.fragment;


import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.activity.AbsBaseActivity;
import come.evernote.evernote.controler.adapter.NotesBookAdapter;
import come.evernote.evernote.model.bean.NoteBookBean;
import come.evernote.evernote.model.bean.SaveBean;
import come.evernote.evernote.model.db.LiteOrmInstance;

/**
 * 笔记本界面
 */

public class TextNotesBookFragment extends ABSBaseFragment {

    private ListView listView;
    private List<NoteBookBean> bean;
    private Set<String> strings = new HashSet<>();

    public static TextNotesBookFragment newInstance() {
        TextNotesBookFragment fragment = new TextNotesBookFragment();
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_text_notes_book;
    }

    @Override
    protected void initView() {
        listView = byView(R.id.notes_book_list);
    }

    @Override
    protected void initData() {
        NoteBookBean noteBook = new NoteBookBean();
        bean = new ArrayList<>();
        List<SaveBean> saveBeen = LiteOrmInstance.getLiteOrmInstance().queryAll(SaveBean.class);
        for (int i = 0; i < saveBeen.size(); i++) {
            strings.add(saveBeen.get(i).getNoteName());
        }
        for (int i = 0; i < strings.size(); i++) {
            noteBook.setNoteName(strings.toArray()[i].toString());
            noteBook.setSize(LiteOrmInstance.getLiteOrmInstance().queryByName(strings.toArray()[i].toString(),SaveBean.class).size());
            bean.add(noteBook);
        }
        NotesBookAdapter bookAdapter = new NotesBookAdapter(context);
        listView.setAdapter(bookAdapter);
        bookAdapter.setList(bean);
    }

    @Override
    public void onResume() {
        super.onResume();
        AbsBaseActivity.setTitle("笔记本");
    }
}
