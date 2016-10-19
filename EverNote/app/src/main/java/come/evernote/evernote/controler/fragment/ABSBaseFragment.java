package come.evernote.evernote.controler.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import come.evernote.evernote.controler.activity.AbsBaseActivity;

/**
 * Created by dllo on 16/10/19.
 */
public abstract class ABSBaseFragment extends Fragment {
    protected Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayout(),container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 设置布局
     */
    protected abstract int setLayout();

    /**
     * 初始化组件
     */
    protected abstract void initView();

    /**
     * 使用数据
     */
    protected abstract void initData();

    /**
     * 简化findViewById
     */
    protected <T extends View> T byView (int resId){
        return (T) getView().findViewById(resId);
    }

    /**
     * 跳转
     */
    protected void goTo(Class<? extends AbsBaseActivity> to){
        context.startActivity(new Intent(context , to));
    }
    /**
     * 跳转传值
     */
    protected void goTo(Context from, Class<? extends AbsBaseActivity> to , Bundle extras){
        Intent intent = new Intent(from,to);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
