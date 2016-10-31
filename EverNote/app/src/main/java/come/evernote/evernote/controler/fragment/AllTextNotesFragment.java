package come.evernote.evernote.controler.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.activity.TextNotesActivity;
import come.evernote.evernote.controler.adapter.AllTextNotesAdapter;
import come.evernote.evernote.model.bean.SaveBean;
import come.evernote.evernote.model.db.LiteOrmInstance;
import come.evernote.evernote.view.ListViewForScrollView;


public class AllTextNotesFragment extends ABSBaseFragment {

    private ListView listView;
    private RelativeLayout emityRl;
    private AllTextNotesAdapter adapter;

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
        listView = byView(R.id.all_text_list);
        emityRl = byView(R.id.all_text_empty_rl);
    }

    @Override
    protected void initData() {
        adapter = new AllTextNotesAdapter(context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SaveBean saveBean = (SaveBean) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                Log.d("ddd", saveBean.getAllContent()+"suiadgb");
                bundle.putSerializable("saveBean", saveBean);
                goTo(context, TextNotesActivity.class, bundle);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<SaveBean> been = LiteOrmInstance.getLiteOrmInstance().queryAll();
        if (been.size() > 0) {
            emityRl.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter.setBeen(been);
        } else {
            listView.setVisibility(View.GONE);
            emityRl.setVisibility(View.VISIBLE);
        }
    }
}
