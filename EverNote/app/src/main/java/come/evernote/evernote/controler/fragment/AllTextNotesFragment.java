package come.evernote.evernote.controler.fragment;



import android.os.Bundle;

import come.evernote.evernote.R;


public class AllTextNotesFragment extends ABSBaseFragment {


    public static AllTextNotesFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AllTextNotesFragment fragment = new AllTextNotesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_all_text_notes;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }
}
