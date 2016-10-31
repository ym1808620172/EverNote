package come.evernote.evernote.controler.fragment;



import android.os.Bundle;

import come.evernote.evernote.R;

/**
 * 废纸篓界面
 */

public class WasteBinFragment extends ABSBaseFragment {

    public static WasteBinFragment newInstance() {
        
        Bundle args = new Bundle();
        
        WasteBinFragment fragment = new WasteBinFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.fragment_waste_bin;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
