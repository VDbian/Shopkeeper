package com.admin.shopkeeper.ui.fragment.setting;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.BossUserInfo;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/29.
 */

class SettingPresenter extends BasePresenter<ISettingView> {

    SettingPresenter(Context context, ISettingView iView) {
        super(context, iView);
    }

    public void queryUserInfo(String shopId) {
        DialogUtils.showDialog(context, "查询信息...");
        Disposable ttt = RetrofitHelper.getInstance()
                .getApi()
                .queryUserInfo("1", shopId)
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(StringModel -> {
                    DialogUtils.hintDialog();
                    switch (StringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if(!TextUtils.isEmpty(StringModel.getResult())){
                                BossUserInfo[] bossUserInfo = new Gson().fromJson(StringModel.getResult(), BossUserInfo[].class);
                                iView.getInfoSuccess(Arrays.asList(bossUserInfo));
                            }

                            break;
                        case Config.REQUEST_FAILED:
                            if(!TextUtils.isEmpty(StringModel.getMessage())){
                                iView.showFailed(StringModel.getMessage());
                            }else{
                                iView.showFailed("获取用户信息失败");
                            }
                            break;
                        case Config.REQUEST_ERROR:
                            if(!TextUtils.isEmpty(StringModel.getMessage())){
                                iView.showFailed(StringModel.getMessage());
                            }else{
                                iView.showFailed("获取用户信息失败");
                            }
                            break;

                    }

                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    public void checkData() {

        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .checkData("6", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {

                    } else {
                        iView.showError("校验失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.showError("校验失败");
                });
    }
}
