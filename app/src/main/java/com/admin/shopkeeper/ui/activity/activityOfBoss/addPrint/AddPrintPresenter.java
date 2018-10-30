package com.admin.shopkeeper.ui.activity.activityOfBoss.addPrint;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.edit.IEditView;
import com.admin.shopkeeper.utils.DialogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class AddPrintPresenter extends BasePresenter<IAddPrinyView> {

    public AddPrintPresenter(Context context, IAddPrinyView iView) {
        super(context, iView);
    }

    public void submit(String id, String name, String port, String address, int printtype, int qiedao, int types, int cuttype, int printspec ,int printWay) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .addPrint(TextUtils.isEmpty(id) ? "2" : "3", id, name, port, address, printtype, qiedao, types, cuttype, printspec, printWay, App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("添加成功");
                    } else {
                        iView.error("提交失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("提交失败");
});
        }
}
