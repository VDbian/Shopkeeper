package com.admin.shopkeeper.ui.activity.activityOfBoss.seasonedit;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.kouweiedit.IKouweiEditView;
import com.admin.shopkeeper.utils.DialogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class SeasonEditPresenter extends BasePresenter<ISeasonEditView> {

    private Object areaData;

    public SeasonEditPresenter(Context context, ISeasonEditView iView) {
        super(context, iView);
    }

    public void commit(String name, String price, String productId) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .addSeason("2", name, price, App.INSTANCE().getShopID(), productId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("提交成功");
                    } else {
                        iView.error("提交失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("提交失败");
                });
    }
}
