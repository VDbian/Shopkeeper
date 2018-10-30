package com.admin.shopkeeper.ui.activity.recharge;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.RechargeBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class RechargePresenter extends BasePresenter<IRechargeView> {


    public RechargePresenter(Context context, IRechargeView iView) {
        super(context, iView);
    }

    public void getData(int page) {
        RetrofitHelper.getInstance()
                .getApi()
                .getRechargeMember("1", App.INSTANCE().getShopID(), page + "", "20", "", "")
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("0")) {
                            iView.error("没有更多数据");
                        } else {
                            RechargeBean[] beens = new Gson().fromJson(stringModel.getResult(), RechargeBean[].class);
                            iView.success(Arrays.asList(beens));
                        }

                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    iView.error("加载失败");
                });
    }

    /**
     * 查询
     *
     * @param
     */
    public void serchData(String name, String phone) {
        DialogUtils.showDialog(context, "数据查询中...");
        RetrofitHelper.getInstance()
                .getApi()
                .getRechargeMember("1", App.INSTANCE().getShopID(), "", "", name, phone)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("0")) {
                            iView.error("没有数据");
                        } else {
                            RechargeBean[] beens = new Gson().fromJson(stringModel.getResult(), RechargeBean[].class);
                            iView.searchSuccess(Arrays.asList(beens));
                        }
                    } else {
                        iView.error("查询失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("查询失败");
                });
    }
}
