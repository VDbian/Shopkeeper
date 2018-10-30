package com.admin.shopkeeper.ui.activity.rechargeedit;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.weightEdit.IWeightEditView;
import com.admin.shopkeeper.utils.DialogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class RechargeEditPresenter extends BasePresenter<IRechargeEditView> {

    public RechargeEditPresenter(Context context, IRechargeEditView iView) {
        super(context, iView);
    }

    public void commit(String phone, String name) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .addRechage("8", App.INSTANCE().getShopID(), phone, name ,App.INSTANCE().getUser().getName() ,App.INSTANCE().getUser().getId())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getResult().equals("1")) {
                        iView.success("提交成功");
                    } else if(stringModel.getResult().equals("0")){
                        iView.error("提交失败");
                    }else{
                        iView.error("该号码已注册");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("提交失败");
                });
    }
}
