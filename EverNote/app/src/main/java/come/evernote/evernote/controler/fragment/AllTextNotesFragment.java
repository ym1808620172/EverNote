package come.evernote.evernote.controler.fragment;



import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.util.StateSet;

import come.evernote.evernote.R;
import come.evernote.evernote.view.CenterTextView;


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
