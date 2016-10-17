package come.evernote.evernote.controler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dllo on 16/10/17.
 * Fragment的基类
 * @author duxiand
 */
public abstract class AbsBaseFragment extends Fragment{
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayout(),container,false);
    }

    /**
     * 设置布局
     * @return 返回R.Layout.xx
     */
    protected abstract int setLayout();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 初始化组件
     */
    protected abstract void initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        
    }

    /**
     * 逻辑代码
     */
    protected abstract void initData();

}
