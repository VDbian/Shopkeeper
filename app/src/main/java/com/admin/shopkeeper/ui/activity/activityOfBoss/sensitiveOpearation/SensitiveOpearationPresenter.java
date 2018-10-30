package com.admin.shopkeeper.ui.activity.activityOfBoss.sensitiveOpearation;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.admin.shopkeeper.entity.SensitiveOpearation;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class SensitiveOpearationPresenter extends BasePresenter<ISensitiveOpearationView> {

    public SensitiveOpearationPresenter(Context context, ISensitiveOpearationView iView) {
        super(context, iView);
    }

    public void getData(int page, String startDate, String endDate, String startTime, String endTime, int selectType,int sensitiveType ) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getSensitiveopearation("3", 20, page, "ASC", startDate, endDate, startTime, endTime, App.INSTANCE().getShopID(), sensitiveType, selectType)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        SensitiveOpearation[] beens = new Gson().fromJson(stringModel.getResult(), SensitiveOpearation[].class);
                        iView.success(Arrays.asList(beens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
