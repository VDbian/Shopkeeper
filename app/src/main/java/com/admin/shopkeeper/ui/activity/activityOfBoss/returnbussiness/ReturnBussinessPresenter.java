package com.admin.shopkeeper.ui.activity.activityOfBoss.returnbussiness;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.FoodBussinessBean;
import com.admin.shopkeeper.entity.ReturnBussinessBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.foodbussiness.IFoodBussinessView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ReturnBussinessPresenter extends BasePresenter<IReturnBussinessView> {

    public ReturnBussinessPresenter(Context context, IReturnBussinessView iView) {
        super(context, iView);
    }

    public void getData(String startTime, String endTime) {

        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getDeskBussiness("6", startTime, endTime ,App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        ReturnBussinessBean[] printBeens = new Gson().fromJson(stringModel.getResult(), ReturnBussinessBean[].class);
                        iView.success(Arrays.asList(printBeens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
