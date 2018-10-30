package com.admin.shopkeeper.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.admin.shopkeeper.utils.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/28.
 */

public abstract class BaseFragment<T extends BasePresenter> extends RxFragment {
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

    /**
     * 显示成功Toast
     *
     * @param text
     */
    public void showSuccessToast(String text) {
        ToastUtils.showToast(getContext(), text, ToastUtils.TOAST_SUCCESS);
    }

    /**
     * 显示失败Toast
     *
     * @param text
     */
    public void showFailToast(String text) {
        ToastUtils.showToast(getContext(), text, ToastUtils.TOAST_FAILE);
    }

    /**
     * 提示Toast
     *
     * @param text
     */
    public void showToast(String text) {
        ToastUtils.showToast(getContext(), text, ToastUtils.TOAST_TIPS);
    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(getActivity(), cls));
    }
}

