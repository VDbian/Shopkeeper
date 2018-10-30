package com.admin.shopkeeper.ui.activity.activityOfBoss.memberLevelManage;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.KouweiBean;
import com.admin.shopkeeper.entity.MemberInfoBean;
import com.admin.shopkeeper.entity.MemberLevelManageBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MemberLevelManagePresenter extends BasePresenter<IMemberLevelManageView> {

    public MemberLevelManagePresenter(Context context, IMemberLevelManageView iView) {
        super(context, iView);
    }

    public void getMemberLevelInfo() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getMembersLevelInfo("1", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        MemberLevelManageBean[] memberLevelManageBeen = new Gson().fromJson(stringModel.getResult(), MemberLevelManageBean[].class);
                        iView.success(Arrays.asList(memberLevelManageBeen));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void deleteMemberLevel(String guid) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .deleteMemberLevel("2", guid)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("删除成功");
                        getMemberLevelInfo();
                    } else {
                        iView.error("删除失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("删除失败");
                });

    }


    public void getRechargeManageInfo() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getRechargeInfo("1", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        MemberLevelManageBean[] memberLevelManageBeen = new Gson().fromJson(stringModel.getResult(), MemberLevelManageBean[].class);
                        iView.success(Arrays.asList(memberLevelManageBeen));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
