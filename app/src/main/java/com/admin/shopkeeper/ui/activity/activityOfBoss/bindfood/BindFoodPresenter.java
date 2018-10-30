package com.admin.shopkeeper.ui.activity.activityOfBoss.bindfood;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.FindFoodCouponDownBean;
import com.admin.shopkeeper.entity.SaleBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class BindFoodPresenter extends BasePresenter<IBindFoodView> {

    public BindFoodPresenter(Context context, IBindFoodView iView) {
        super(context, iView);
    }


    public void getFood(SaleBean bean, String name) {
        RetrofitHelper.getInstance()
                .getApi()
                .getSaleBindFood("5", 20, 1, name, bean.getGuiId(), App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("0")) {
                            iView.error("加载失败");
                        } else {
                            FindFoodCouponDownBean[] been = new Gson().fromJson(stringModel.getResult(), FindFoodCouponDownBean[].class);
                            iView.success(Arrays.asList(been));
                        }
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    iView.error("加载失败");
                });
    }


}
