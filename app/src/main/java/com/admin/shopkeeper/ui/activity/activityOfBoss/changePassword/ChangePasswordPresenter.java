package com.admin.shopkeeper.ui.activity.activityOfBoss.changePassword;

import android.content.Context;
import android.util.Log;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ChangePasswordPresenter extends BasePresenter<IChangePasswordView> {

    public ChangePasswordPresenter(Context context, IChangePasswordView iView) {
        super(context, iView);
    }

    public void changePassword(String oldPassword, String newPassword) {
        DialogUtils.showDialog(context, "正在修改");
        RetrofitHelper.getInstance()
                .getApi()
                .changePassword("5", App.INSTANCE().getShopID(),oldPassword,newPassword)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    Log.d("ttt",stringModel.getCode() + stringModel.getResult());
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
