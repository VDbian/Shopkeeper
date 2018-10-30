package com.admin.shopkeeper.ui.activity.main;

import android.content.Context;

import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.helper.RetrofitHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by admin on 2017/3/27.
 */
class MainPresenter extends BasePresenter<IMainView> {
    MainPresenter(Context context, IMainView iView) {
        super(context, iView);
    }

//    void test() {
//
//
//        RetrofitHelper.getInstance().getApi().test("id", "name", "password").observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .compose(getActivityLifecycleProvider().<Object>bindToLifecycle())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//
//                        Timber.d(o.toString());
//                    }
//
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });
//
//    }
}
