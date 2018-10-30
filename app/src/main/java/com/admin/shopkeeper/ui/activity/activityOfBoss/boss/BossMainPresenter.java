package com.admin.shopkeeper.ui.activity.activityOfBoss.boss;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.BossUserInfo;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.utils.DialogUtils;
import com.admin.shopkeeper.utils.SPUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class BossMainPresenter extends BasePresenter<IBossMainView> {

    public BossMainPresenter(Context context, IBossMainView iView) {
        super(context, iView);
    }

    public void queryUserInfo(String shopID) {
        Disposable ttt = RetrofitHelper.getInstance()
                .getApi()
                .queryUserInfo("1", shopID)
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        BossUserInfo[] bossUserInfo = new Gson().fromJson(stringModel.getResult(), BossUserInfo[].class);
                        iView.getInfoSuccess(Arrays.asList(bossUserInfo));
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
                        iView.error("校验失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("校验失败");
                });
    }

    public void checkShop() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .checkShop("26",  SPUtils.getInstance().getString(SPUtils.PREFERENCE_SHOP_ID))
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        ChainBean[] chainBeen = new Gson().fromJson(stringModel.getResult(), ChainBean[].class);
                        iView.getChainInfoSuccess(Arrays.asList(chainBeen));
                    } else {
                        iView.error("检索失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("检索失败");
                });
    }
}
