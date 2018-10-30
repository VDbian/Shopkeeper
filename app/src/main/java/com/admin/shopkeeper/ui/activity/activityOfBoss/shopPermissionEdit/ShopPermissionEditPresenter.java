package com.admin.shopkeeper.ui.activity.activityOfBoss.shopPermissionEdit;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.PermissionsOfUser;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ShopPermissionEditPresenter extends BasePresenter<IShopPermissionEditView> {
    public ShopPermissionEditPresenter(Context context, IShopPermissionEditView iView) {
        super(context, iView);
    }

    public void getPermission(String userID) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getPermissionsOfUser("2", userID)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        PermissionsOfUser[] permissionsOfUsers = new Gson().fromJson(stringModel.getResult(), PermissionsOfUser[].class);
                        iView.success(Arrays.asList(permissionsOfUsers));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    /**
     * 修改权限
     */
    public void changeUserPermission(String userID, String permissionJson) {
        DialogUtils.showDialog(context, "权限修改中");
        RetrofitHelper.getInstance()
                .getApi()
                .changePermissionsOfUser("3", userID, permissionJson)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("修改成功");
                    } else {
                        iView.error("修改失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("修改失败");
                });
    }
}
