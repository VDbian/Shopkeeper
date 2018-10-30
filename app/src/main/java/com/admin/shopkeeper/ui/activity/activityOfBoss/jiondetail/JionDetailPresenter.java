package com.admin.shopkeeper.ui.activity.activityOfBoss.jiondetail;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.admin.shopkeeper.entity.GuazhangDetailBean;
import com.admin.shopkeeper.entity.HandoverDetailBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.guazhangdetail.IGuaZhangDetailView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class JionDetailPresenter extends BasePresenter<IJionDetailView> {

    public JionDetailPresenter(Context context, IJionDetailView iView) {
        super(context, iView);
    }


    public void getData(String guid) {
        DialogUtils.showDialog(context, "加载中...");
        RetrofitHelper.getInstance()
                .getApi()
                .getJionDetail("17", guid)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        HandoverDetailBean[] beens = new Gson().fromJson(stringModel.getResult(), HandoverDetailBean[].class);
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
