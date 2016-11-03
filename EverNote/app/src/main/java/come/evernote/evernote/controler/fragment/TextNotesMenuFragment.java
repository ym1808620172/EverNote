package come.evernote.evernote.controler.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import come.evernote.evernote.R;

/**
 * Created by dllo on 16/10/18.
 * 在文字笔记界面点击左上角的小点弹出的抽屉界面
 *
 * @author 杜显东
 */
public class TextNotesMenuFragment extends ABSBaseFragment implements View.OnClickListener {
    private LinearLayout saveLayout;//保存
    private LinearLayout shareLayout;//共享笔记
    private LinearLayout searchLayout;//笔记内搜索
    private LinearLayout connectionLayout;//复制笔记本连接
    private LinearLayout duplicateLayout;//复制
    private LinearLayout shortcutsLayout;//添加快捷方式
    private LinearLayout screenLayout;//添加到手机主屏幕
    private LinearLayout simplifiedLayout;//简化格式
    private LinearLayout settingLayout;//设置
    private LinearLayout deleteLayout;//删除笔记
    private ProgressDialog progressDialog;

    public static TextNotesMenuFragment newInstance() {

        Bundle args = new Bundle();

        TextNotesMenuFragment fragment = new TextNotesMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.frament_menu_notes_text;
    }

    @Override
    protected void initView() {
        saveLayout = byView(R.id.menu_save);
        shareLayout = byView(R.id.menu_share);
        searchLayout = byView(R.id.menu_search);
        connectionLayout = byView(R.id.menu_connection);
        duplicateLayout = byView(R.id.menu_duplicate);
        shortcutsLayout = byView(R.id.menu_shortcuts);
        simplifiedLayout = byView(R.id.menu_simplified_form);
        settingLayout = byView(R.id.menu_setting);
        deleteLayout = byView(R.id.menu_delete);
        screenLayout = byView(R.id.menu_screen);
        saveLayout.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        connectionLayout.setOnClickListener(this);
        duplicateLayout.setOnClickListener(this);
        shortcutsLayout.setOnClickListener(this);
        simplifiedLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        deleteLayout.setOnClickListener(this);
        screenLayout.setOnClickListener(this);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在保存笔记...");

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_save:
                Toast.makeText(context, "保存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_share:
                Toast.makeText(context, "共享笔记", Toast.LENGTH_SHORT).show();
                progressDialog.show();
                break;
            case R.id.menu_search:
                Toast.makeText(context, "笔记内搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_connection:
                Toast.makeText(context, "复制笔记本连接", Toast.LENGTH_SHORT).show();
                progressDialog.show();
                break;
            case R.id.menu_duplicate:
                Toast.makeText(context, "复制", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_shortcuts:
                Toast.makeText(context, "添加快捷方式", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_simplified_form:
                Toast.makeText(context, "简化格式", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_setting:
                Toast.makeText(context, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_delete:
                Toast.makeText(context, "删除笔记", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_screen:
                Toast.makeText(context, "添加到手机主屏幕", Toast.LENGTH_SHORT).show();
                break;

        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    progressDialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
