package come.evernote.evernote.controler.fragment;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import come.evernote.evernote.R;
import come.evernote.evernote.controler.Interfaces.MenuDeleteI;
import come.evernote.evernote.controler.activity.AbsBaseActivity;
import come.evernote.evernote.controler.adapter.WasteBinAdapter;
import come.evernote.evernote.model.bean.WasteBinBean;
import come.evernote.evernote.model.db.LiteOrmInstance;

/**
 * 废纸篓界面
 */

public class WasteBinFragment extends ABSBaseFragment implements MenuDeleteI {
    private ListView listView;
    private RelativeLayout emityRl;
    private WasteBinAdapter adapter;
    private List<Integer> integerList;

    public static WasteBinFragment newInstance() {
        WasteBinFragment fragment = new WasteBinFragment();
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
        integerList = new ArrayList<>();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setChickBox(position);
                adapter.setShow(true);
                integerList.add(position);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<WasteBinBean> been = LiteOrmInstance.getLiteOrmInstance().queryAll(WasteBinBean.class);
        adapter.setBeen(been);
        listView.setEmptyView(emityRl);
        AbsBaseActivity.setTitle("废纸篓");
    }

    @Override
    public void onDeleteWasteBin(int position) {
        if (adapter != null) {
            if (position == 1) {
                adapter.setShow(true);
            } else {
                if (integerList != null) {
                    for (int i = 0; i < integerList.size(); i++) {
                        LiteOrmInstance.getLiteOrmInstance().deleteById(integerList.get(i), WasteBinBean.class);
                    }
                    List<WasteBinBean> been = LiteOrmInstance.getLiteOrmInstance().queryAll(WasteBinBean.class);
                    adapter.setBeen(been);
                    listView.setEmptyView(emityRl);
                }
            }
        }
    }
}
