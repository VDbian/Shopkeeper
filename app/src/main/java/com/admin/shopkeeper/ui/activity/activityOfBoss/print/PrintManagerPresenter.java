package com.admin.shopkeeper.ui.activity.activityOfBoss.print;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.PrintBean;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.returncause.IReturnCauseView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class PrintManagerPresenter extends BasePresenter<IPrintManagerView> {

    public PrintManagerPresenter(Context context, IPrintManagerView iView) {
        super(context, iView);
    }

    public void getData() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getPrint("1", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        PrintBean[] printBeens = new Gson().fromJson(stringModel.getResult(), PrintBean[].class);
                        iView.success(Arrays.asList(printBeens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });

    }

    public void delete(RetCauseBean bean) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .deleteCause("3", App.INSTANCE().getShopID(), bean.getGuid())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("删除成功");
                    } else {
                        iView.error("删除失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("删除失败");
                });

    }

    public void delete(PrintBean bean) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .deletePrint("4", bean.getId(), App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("删除成功");
                        getData();
                    } else {
                        iView.error("删除失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("删除失败");
                });
    }
}
