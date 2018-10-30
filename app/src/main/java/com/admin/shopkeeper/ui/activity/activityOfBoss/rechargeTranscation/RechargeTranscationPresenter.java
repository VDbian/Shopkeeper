package com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeTranscation;

import android.content.Context;

import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.MemberTranscationBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/7.
 */

public class RechargeTranscationPresenter extends BasePresenter<IRechargeTranscationView> {

    public RechargeTranscationPresenter(Context context, IRechargeTranscationView iView) {
        super(context, iView);
    }

    public void getData(int pageIndex, String startDate, String endDate, String startTime, String endTime, int type, String shopId) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getMemberTranscation("11", 20, pageIndex, "ASC", startDate, endDate,
                        startTime, endTime, shopId, type)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("")) {
                            iView.error("查询数据为空");
                            iView.success(new ArrayList<>());
                        } else {
                            MemberTranscationBean[] beens = new Gson().fromJson(stringModel.getResult(), MemberTranscationBean[].class);
                            iView.success(Arrays.asList(beens));
                        }
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    iView.error("加载失败");
                    DialogUtils.hintDialog();
                });
    }


}
