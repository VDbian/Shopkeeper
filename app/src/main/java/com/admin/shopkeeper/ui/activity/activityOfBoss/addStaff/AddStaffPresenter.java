package com.admin.shopkeeper.ui.activity.activityOfBoss.addStaff;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.DepartmentBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.ShopPermissionManageBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.utils.DeviceUtils;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/8/24.
 */

public class AddStaffPresenter extends BasePresenter<IAddStaffView> {

    public AddStaffPresenter(Context context, IAddStaffView iView) {
        super(context, iView);
    }

    public void submit(String userId, String staffAccount, String staffPassword, int stateRole, int stateSex,
                       String birthday, String departmentId) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .addStaff(TextUtils.isEmpty(userId) ? "7" : "8", staffAccount, staffPassword, App.INSTANCE().getShopID(), stateRole, stateSex, birthday, TextUtils.isEmpty(userId) ? "" : userId, departmentId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (TextUtils.isEmpty(userId)) {
                            iView.success("添加成功");
                        } else {
                            iView.success("修改成功");
                        }
                    } else {
                        if (TextUtils.isEmpty(userId)) {
                            iView.error("添加失败");
                        } else {
                            iView.error("修改失败");
                        }
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    if (TextUtils.isEmpty(userId)) {
                        iView.error("添加失败");
                    } else {
                        iView.error("修改失败");
                    }
                });
    }


    public void getDepartMentId() {
        RetrofitHelper.getInstance()
            .getApi()
            .getDepartMent("9")
            .compose(getActivityLifecycleProvider().bindToLifecycle())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(stringModel -> {
                DialogUtils.hintDialog();
                if (stringModel.getCode().equals("1")) {
                    if (stringModel.getResult().equals("0")) {

                    } else {
                        DepartmentBean[] departmentBean = new Gson().fromJson(stringModel.getResult(), DepartmentBean[].class);
                        iView.success(Arrays.asList(departmentBean));
                    }
                } else {
                }
            }, throwable -> {
            });

    }
}
