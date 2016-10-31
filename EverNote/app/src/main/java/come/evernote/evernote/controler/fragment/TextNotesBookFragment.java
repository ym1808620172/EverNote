package come.evernote.evernote.controler.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import come.evernote.evernote.R;

/**
 * 笔记本界面
 */

public class TextNotesBookFragment extends ABSBaseFragment {

    public static TextNotesBookFragment newInstance() {

        Bundle args = new Bundle();

        TextNotesBookFragment fragment = new TextNotesBookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_text_notes_book;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
