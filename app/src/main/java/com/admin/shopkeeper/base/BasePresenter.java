package com.admin.shopkeeper.base;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.entity.TableEntity;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/28.
 */

public class BasePresenter<T extends IBaseView> {

    public Context context;
    public T iView;

    public BasePresenter(Context context, T iView) {
        this.context = context;
        this.iView = iView;

    }

    /**
     * 初始化
     */
    public void init() {
        iView.initView();
    }


    /**
     * 对 ACTIVITY 生命周期进行管理
     *
     * @return
     */

    protected LifecycleProvider<ActivityEvent> getActivityLifecycleProvider() {
        LifecycleProvider<ActivityEvent> provider = null;
        if (null != context && context instanceof LifecycleProvider) {
            provider = (LifecycleProvider<ActivityEvent>) context;
        }
        return provider;
    }

    protected LifecycleProvider<FragmentEvent> getFragmentLifecycleProvider() {
        LifecycleProvider<FragmentEvent> provider = null;
        if (null != context && context instanceof LifecycleProvider) {
            provider = (LifecycleProvider<FragmentEvent>) context;
        }
        return provider;
    }

    protected void doError(Throwable error) {
        // TODO 此处可处理错误
    }


    public void doDestroy() {
        this.context = null;
    }



}
