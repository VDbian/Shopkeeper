package com.admin.shopkeeper.ui.fragment.home;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.BussinessBean;
import com.admin.shopkeeper.entity.FoodBussinessBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/29.
 */

class HomePresenter extends BasePresenter<IHomeView> {


    HomePresenter(Context context, IHomeView iView) {
        super(context, iView);
    }

    public void getData() {
        RetrofitHelper.getInstance()
                .getApi()
                .getBussinee("10", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("0")) {
                            iView.error("加载失败");
                        } else {
                            BussinessBean[] bussinessBeen = new Gson().fromJson(stringModel.getResult(), BussinessBean[].class);
                            iView.success(Arrays.asList(bussinessBeen));
                        }
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    iView.error("加载失败");
                });
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
                        if(type.equals("8"))
                            iView.successOfHotFood(Arrays.asList(printBeens));
                        else
                            iView.successOfCoolFood(Arrays.asList(printBeens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
