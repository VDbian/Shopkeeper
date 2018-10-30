package com.admin.shopkeeper.ui.activity.activityOfBoss.guazhangedit;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.houseEdit.IHouseEditView;
import com.admin.shopkeeper.utils.DialogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class GuaZhangEditPresenter extends BasePresenter<IGuaZhangEditView> {

    public GuaZhangEditPresenter(Context context, IGuaZhangEditView iView) {
        super(context, iView);
    }

    public void commit(String guid, String name, String phone, String remark) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .editGuazhang(TextUtils.isEmpty(guid) ? "3" : "4", name, phone, remark, guid, App.INSTANCE().getShopID())
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
