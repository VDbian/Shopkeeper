package com.admin.shopkeeper.ui.activity.splash;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;

import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.QueueBean;
import com.admin.shopkeeper.entity.TableEntity;
import com.admin.shopkeeper.entity.User;
import com.admin.shopkeeper.entity.VersionBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.admin.shopkeeper.utils.SPUtils;
import com.admin.shopkeeper.utils.Tools;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Created by zeng on 2017/3/28.
 */

class SplashPresenter extends BasePresenter<ISplashView> {
    private Object update;

    SplashPresenter(Context context, ISplashView iView) {
        super(context, iView);
    }

    void getConfig() {
        String id = SPUtils.getInstance().getString(SPUtils.PREFERENCE_SHOP_ID);
        if (!TextUtils.isEmpty(id)) {
            App.INSTANCE().setShopID(id);
        }
    }

    void getUser() {
        AppDbHelper.INSTANCE().getUser()
                .compose(getActivityLifecycleProvider().<User>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(user -> {
                    App.INSTANCE().setUser(user);
                }, Throwable::printStackTrace);
    }

    public void getUpdate() {
        RetrofitHelper.getInstance()
                .getApi()
                .getVersion("6")
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        VersionBean[] versionBeens = new Gson().fromJson(stringModel.getResult(), VersionBean[].class);
                        for (VersionBean versionBean : versionBeens) {
                            if (versionBean.getVersionType().equals("A")) {
                                iView.update(Integer.parseInt(versionBean.getVersionCode()));
                                break;
                            }
                        }
                    } else {
                        iView.error("获取数据失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("获取数据失败，接口异常或网络异常");
                });
    }
}
