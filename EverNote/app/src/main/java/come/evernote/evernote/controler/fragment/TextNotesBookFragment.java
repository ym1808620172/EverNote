package come.evernote.evernote.controler.fragment;


import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
    private RelativeLayout rl;
    private LinearLayout head_layout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CoordinatorLayout root_layout;
    private AppBarLayout app_bar_layout;
    private Toolbar mToolbar;

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
        app_bar_layout = byView(R.id.text_book_bar_layout);
        mToolbar = byView(R.id.text_book_toolbar);
        head_layout = byView(R.id.head_layout);
        root_layout = byView(R.id.text_book_root_layout);
        mCollapsingToolbarLayout = byView(R.id.text_book_collapsing_toolbar_layout);
        listView = byView(R.id.notes_book_list);
//        rl = byView(R.id.notes_empity_rl);
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
            noteBook.setSize(LiteOrmInstance.getLiteOrmInstance().queryByName(strings.toArray()[i].toString(), SaveBean.class).size());
            bean.add(noteBook);
        }
        NotesBookAdapter bookAdapter = new NotesBookAdapter(context);
        listView.setAdapter(bookAdapter);
        bookAdapter.setList(bean);
//        listView.setEmptyView(rl);

    }

    @Override
    public void onResume() {
        super.onResume();
        AbsBaseActivity.setTitle("笔记本");
    }
}
