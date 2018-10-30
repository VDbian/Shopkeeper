package com.admin.shopkeeper.ui.activity.activityOfBoss.sensitiveOpearationDetail;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.MemberTranscationBean;
import com.admin.shopkeeper.entity.OrderManageDetail;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/7.
 */

public class SensitiveDetailPresenter extends BasePresenter<ISensitiveDetailView> {

    public SensitiveDetailPresenter(Context context, ISensitiveDetailView iView) {
        super(context, iView);
    }

    public void getSensitiveDetail(String billId) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getSensitiveopearationDetail("5", billId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        OrderManageDetail[] beens = new Gson().fromJson(stringModel.getResult(), OrderManageDetail[].class);
                        iView.success(Arrays.asList(beens));
                    } else {
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                });
    }
}
