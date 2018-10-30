package com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeDetail;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.MemberTranscationBean;
import com.admin.shopkeeper.entity.RechargeDetailTableBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/7.
 */

public class RechargeDetailPresenter extends BasePresenter<IRechargeDetailView> {

    public RechargeDetailPresenter(Context context, IRechargeDetailView iView) {
        super(context, iView);
    }

    public void getData(int pageIndex, String startDate, String endDate, String startTime, String endTime, int type) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getMemberTranscation("12", 20, pageIndex, "ASC", startDate, endDate, startTime, endTime, App.INSTANCE().getShopID(), type)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        MemberTranscationBean[] beens = new Gson().fromJson(stringModel.getResult(), MemberTranscationBean[].class);
//                        iView.success(Arrays.asList(beens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void getData(String startDate, String endDate, String shopId) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getRechargeDetail("10", "ASC", startDate, endDate, shopId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (TextUtils.isEmpty(stringModel.getResult())) {
                            iView.success("暂无数据");
                        } else {
                            RechargeDetailTableBean[] beens = new Gson().fromJson(stringModel.getResult(), RechargeDetailTableBean[].class);
                            iView.success(Arrays.asList(beens));
                        }
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
