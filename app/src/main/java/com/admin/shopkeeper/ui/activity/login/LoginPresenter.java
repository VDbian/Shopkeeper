package com.admin.shopkeeper.ui.activity.login;

import android.content.Context;
import android.util.Log;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.User;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.IntModel;
import com.admin.shopkeeper.utils.DialogUtils;
import com.admin.shopkeeper.utils.SPUtils;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zeng on 2017/3/28.
 */

class LoginPresenter extends BasePresenter<ILoginView> {

    LoginPresenter(Context context, ILoginView iView) {
        super(context, iView);
    }

    public void login(String u, String p) {
        DialogUtils.showDialog(context, "登录中...");
        RetrofitHelper.getInstance()
                .getApi()
                .login(u, SPUtils.getInstance().getString(SPUtils.PREFERENCE_SHOP_ID), p)
                .compose(getActivityLifecycleProvider().<IntModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                    DialogUtils.hintDialog();
                    //1 普通 2 老板 0 错误
                    switch (o.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            Log.e("vd",o.getResult());
                            User user = new Gson().fromJson(o.getResult(), User.class);
                            if (user != null) {
                                SPUtils.getInstance().put(SPUtils.PREFERENCE_USER, user.getId());
                                App.INSTANCE().setUser(user);
                                saveDB(user);
                            } else {
                                iView.showFailed("登录失败！");
                            }
                            break;
                        case Config.REQUEST_ERROR:
                            iView.showError(context.getString(R.string.string_request_error));
                            break;
                        case Config.REQUEST_FAILED:
                            iView.showError(o.getMessage());
                            break;
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    DialogUtils.hintDialog();
                    iView.showFailed("登录失败");
                });
    }

    private void saveDB(User user) {
        AppDbHelper.INSTANCE().saveUser(user)
                .compose(getActivityLifecycleProvider().<User>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(user1 -> {
                    iView.loginSuccess();
                    SPUtils.getInstance().put(SPUtils.PREFERENCE_LOGIN, true);
                }, Throwable::printStackTrace);
    }

}
