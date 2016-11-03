package come.evernote.evernote.controler.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.activity.AbsBaseActivity;
import come.evernote.evernote.controler.activity.TextNotesActivity;
import come.evernote.evernote.controler.adapter.AllTextNotesAdapter;
import come.evernote.evernote.controler.adapter.WasteBinAdapter;
import come.evernote.evernote.model.bean.NoteNameBean;
import come.evernote.evernote.model.bean.SaveBean;
import come.evernote.evernote.model.db.LiteOrmInstance;

/**
 * 废纸篓界面
 */

public class WasteBinFragment extends ABSBaseFragment {
    private ListView listView;
    private RelativeLayout emityRl;
    private WasteBinAdapter adapter;

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
        listView = byView(R.id.waste_bin_list);
        emityRl = byView(R.id.waste_empty_rl);
    }

    @Override
    protected void initData() {
        adapter = new WasteBinAdapter(context);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<NoteNameBean> been = LiteOrmInstance.getLiteOrmInstance().queryAll(NoteNameBean.class);
        if (been.size() > 0) {
            emityRl.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter.setBeen(been);
        } else {
            listView.setVisibility(View.GONE);
            emityRl.setVisibility(View.VISIBLE);
        }
        AbsBaseActivity.setTitle("废纸篓");
    }
}
