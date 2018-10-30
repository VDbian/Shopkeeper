package com.admin.shopkeeper.ui.activity.activityOfBoss.orderManage;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.OrderManage;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class OrderManagePresenter extends BasePresenter<IOrderManageView> {

    public OrderManagePresenter(Context context, IOrderManageView iView) {
        super(context, iView);
    }

    public void getData(int page, String startDate, String endDate, String startTime, String endTime, int selectType) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getOrderManage("1", 20, page, "ASC", startDate, endDate, startTime, endTime, App.INSTANCE().getShopID(), selectType)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if(stringModel.getResult().equals("0")){
                            iView.success("暂无数据");
                        }else{
                            OrderManage[] beens = new Gson().fromJson(stringModel.getResult(), OrderManage[].class);
                            iView.success(Arrays.asList(beens));
                        }

                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

}
