package com.admin.shopkeeper.ui.activity.activityOfBoss.changeShopName;

import android.content.Context;
import android.util.Log;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ChangeShopNamePresenter extends BasePresenter<IChangeShopNameView> {

    public ChangeShopNamePresenter(Context context, IChangeShopNameView iView) {
        super(context, iView);
    }

    public void changeShopName(String name) {
        DialogUtils.showDialog(context, "正在修改");
        RetrofitHelper.getInstance()
                .getApi()
                .changeShopName("4", App.INSTANCE().getShopID(),name)
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
