package com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeManageEdit;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.DepartmentBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class RechargeManageEditPresenter extends BasePresenter<IRechargeManageEditView> {

    public RechargeManageEditPresenter(Context context, IRechargeManageEditView iView) {
        super(context, iView);
    }

    public void submit(String guId, String name, String chargeMoney, String giveMoney, int stateType) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .editRechargeManage(TextUtils.isEmpty(guId) ? "2" : "3",TextUtils.isEmpty(guId) ? App.INSTANCE().getShopID(): "", TextUtils.isEmpty(guId) ? "" : guId, name, stateType, chargeMoney,  giveMoney)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (TextUtils.isEmpty(guId)) {
                            iView.success("添加成功");
                        } else {
                            iView.success("修改成功");
                        }
                    } else {
                        if (TextUtils.isEmpty(guId)) {
                            iView.error("添加失败");
                        } else {
                            iView.error("修改失败");
                        }
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    if (TextUtils.isEmpty(guId)) {
                        iView.error("添加失败");
                    } else {
                        iView.error("修改失败");
                    }
                });
    }

}
