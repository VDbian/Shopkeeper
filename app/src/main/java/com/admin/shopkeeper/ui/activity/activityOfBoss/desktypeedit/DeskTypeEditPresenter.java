package com.admin.shopkeeper.ui.activity.activityOfBoss.desktypeedit;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.PersonBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.desktype.IDeskTypeView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.houseEdit.IHouseEditView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class DeskTypeEditPresenter extends BasePresenter<IDeskTypeEditView> {

    public DeskTypeEditPresenter(Context context, IDeskTypeEditView iView) {
        super(context, iView);
    }

    public void commit(String guid, String personCount, String personInfo, String types, String name) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .editDesktype(TextUtils.isEmpty(guid) ? "8" : "9", guid, personCount, personInfo, name, types, App.INSTANCE().getShopID())
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

    public void getPersonData() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getDesk("5", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        PersonBean[] personBeens = new Gson().fromJson(stringModel.getResult(), PersonBean[].class);
                        iView.personSuccess(Arrays.asList(personBeens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
