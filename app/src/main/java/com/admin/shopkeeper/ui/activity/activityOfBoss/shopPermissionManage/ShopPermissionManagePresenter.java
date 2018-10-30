package com.admin.shopkeeper.ui.activity.activityOfBoss.shopPermissionManage;

import android.content.Context;
import android.util.Log;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.ShopPermissionManageBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ShopPermissionManagePresenter extends BasePresenter<IShopPermissionManageView> {

    public ShopPermissionManagePresenter(Context context, IShopPermissionManageView iView) {
        super(context, iView);
    }

    public void getData() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getShopPermissionPersonList("1", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        ShopPermissionManageBean[] shopPermissionManageBean = new Gson().fromJson(stringModel.getResult(), ShopPermissionManageBean[].class);
                        iView.success(Arrays.asList(shopPermissionManageBean));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });

    }
}
