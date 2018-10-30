package com.admin.shopkeeper.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;

/**
 * Created by gskl on 16/10/12.
 */

public abstract class DelayFragment<T extends BasePresenter> extends RxFragment {
    protected T presenter;
    public static final String TAG = "fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }

    protected abstract int getLayoutResId();

    protected abstract void initPresenter();
    /*
    * fragment是否可见
    * */
    protected boolean isVisible = false;

    /*
    *实现fragment懒加载
    * */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }

    }

    /*
    *可见
    */
    protected void onVisible() {
        lazyLoad();

    }

    /*
    不可见
    */
    protected void onInvisible() {

    }


    public abstract void lazyLoad();
}
