package come.evernote.evernote.controler.fragment;

import android.os.Bundle;

import come.evernote.evernote.R;

/**
 * Created by dllo on 16/10/18.
 * 在文字笔记界面点击左上角的小点弹出的抽屉界面
 * @author 杜显东
 */
public class TextNotesMenuFragment extends ABSBaseFragment{
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

    }

    @Override
    protected void initData() {

    }
}
