package come.evernote.evernote.controler.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import come.evernote.evernote.model.bean.WasteBinBean;
import come.evernote.evernote.model.bean.SaveBean;
import come.evernote.evernote.model.db.LiteOrmInstance;


public class AllTextNotesFragment extends ABSBaseFragment {

    private ListView listView;
    private RelativeLayout emityRl;
    private AllTextNotesAdapter adapter;

    public static AllTextNotesFragment newInstance() {
        AllTextNotesFragment fragment = new AllTextNotesFragment();
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
        final WasteBinBean bean = new WasteBinBean();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SaveBean saveBean = (SaveBean) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("saveBean", saveBean);
                goTo(context, TextNotesActivity.class, bundle);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setDialog(parent, position, bean);
                return true;
            }
        });
    }


    private void setDialog(AdapterView<?> parent, int position, final WasteBinBean bean) {
        final SaveBean saveBeen = (SaveBean) parent.getItemAtPosition(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("是否删除该数据");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LiteOrmInstance.getLiteOrmInstance().deleteById(bean.getId(), SaveBean.class);
                bean.setNoteName(saveBeen.getNoteName());
                bean.setTitle(saveBeen.getTitle());
                bean.setAllContent(saveBeen.getAllContent());
                bean.setBitmap(saveBeen.getBitmap());
                bean.setDate(saveBeen.getDate());
                bean.setContent(saveBeen.getContent());
                LiteOrmInstance.getLiteOrmInstance().insert(bean);
                List<SaveBean> been = LiteOrmInstance.getLiteOrmInstance().queryAll(SaveBean.class);
                Log.d("ddd", "been.size():" + been.size());
                if (been.size() > 0) {
                    emityRl.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    adapter.setBeen(been);
                } else {
                    listView.setVisibility(View.GONE);
                    emityRl.setVisibility(View.VISIBLE);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        List<SaveBean> been = LiteOrmInstance.getLiteOrmInstance().queryAll(SaveBean.class);
        adapter.setBeen(been);
        listView.setEmptyView(emityRl);
        AbsBaseActivity.setTitle("全部笔记");
    }
}
