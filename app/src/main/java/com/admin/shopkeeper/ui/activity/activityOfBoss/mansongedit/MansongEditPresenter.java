package com.admin.shopkeeper.ui.activity.activityOfBoss.mansongedit;

import android.content.Context;
import android.text.TextUtils;

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

public class MansongEditPresenter extends BasePresenter<IMansongEditView> {

    private Object areaData;

    public MansongEditPresenter(Context context, IMansongEditView iView) {
        super(context, iView);
    }

    public void commit(String guid, String name, String tiaojian, String money, String btime, String etime, int type, int enable) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .editMansong(TextUtils.isEmpty(guid) ? "4" : "5", guid, name, tiaojian, money, btime, etime, type, enable, App.INSTANCE().getShopID())
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
