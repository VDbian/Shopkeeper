package com.admin.shopkeeper.ui.activity.activityOfBoss.memberManager;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.MemberInfoBean;
import com.admin.shopkeeper.entity.ShopPermissionManageBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MemberManagePresenter extends BasePresenter<IMemberManageView> {

    public MemberManagePresenter(Context context, IMemberManageView iView) {
        super(context, iView);
    }

    public void getMemberInfo(int page) {
        RetrofitHelper.getInstance()
                .getApi()
                .getRechargeMember("1", App.INSTANCE().getShopID(), page + "", 20 + "", "", "")
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("0")) {
                            iView.error("没有更多数据");
                        } else {
                            MemberInfoBean[] memberInfoBean = new Gson().fromJson(stringModel.getResult(), MemberInfoBean[].class);
                            iView.success(Arrays.asList(memberInfoBean));
                        }

                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    iView.error("加载失败");
                });
    }

    public void searchMemberInfo(String name, String phone) {
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
                            iView.error("没有该数据");
                        } else {
                            MemberInfoBean[] memberInfoBean = new Gson().fromJson(stringModel.getResult(), MemberInfoBean[].class);
                            iView.searchsuccess(Arrays.asList(memberInfoBean));
                        }

                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void queryInfo(int type, String userID) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getMemberInfo(type + "", userID)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
//                        MemberInfoBean[] memberInfoBean = new Gson().fromJson(stringModel.getResult(), MemberInfoBean[].class);
//                        iView.success(Arrays.asList(memberInfoBean));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
