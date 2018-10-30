package com.admin.shopkeeper.ui.activity.activityOfBoss.salebussiness;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.FoodBussinessBean;
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

public class SaleBussinessPresenter extends BasePresenter<ISaleBussinessView> {

    public SaleBussinessPresenter(Context context, ISaleBussinessView iView) {
        super(context, iView);
    }

    public void getData(String type, String startTime, String endTime) {

        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getDeskBussiness(type, startTime, endTime, App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        FoodBussinessBean[] printBeens = new Gson().fromJson(stringModel.getResult(), FoodBussinessBean[].class);
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
