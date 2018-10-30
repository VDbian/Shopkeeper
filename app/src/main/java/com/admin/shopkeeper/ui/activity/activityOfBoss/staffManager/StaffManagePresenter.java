package com.admin.shopkeeper.ui.activity.activityOfBoss.staffManager;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MemberInfoBean;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.entity.ShopPermissionManageBean;
import com.admin.shopkeeper.entity.StaffManageBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/7.
 */

public class StaffManagePresenter extends BasePresenter<IStaffManageView> {

    public StaffManagePresenter(Context context, IStaffManageView iView) {
        super(context, iView);
    }

    public void getStaffInfo() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getShopPermissionPersonList("5", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        StaffManageBean[] staffManageBeen = new Gson().fromJson(stringModel.getResult(), StaffManageBean[].class);
                        iView.success(Arrays.asList(staffManageBeen));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    /**
     * 删除员工
     * @param bean
     */
    public void deleteStaff(StaffManageBean bean) {
            DialogUtils.showDialog(context, "删除中");
            RetrofitHelper.getInstance()
                    .getApi()
                    .deleteStaff("6", bean.getUserID())
                    .compose(getActivityLifecycleProvider().bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(stringModel -> {
                        DialogUtils.hintDialog();
                        if (stringModel.getCode().equals("1")) {
                            iView.success("删除成功");
                        } else {
                            iView.error("删除失败");
                        }

                    }, throwable -> {
                        DialogUtils.hintDialog();
                        iView.error("删除失败");
                    });
    }
}
