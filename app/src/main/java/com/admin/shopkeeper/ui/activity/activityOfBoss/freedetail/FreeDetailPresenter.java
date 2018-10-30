package com.admin.shopkeeper.ui.activity.activityOfBoss.freedetail;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.FreeDetailBean;
import com.admin.shopkeeper.entity.HandoverDetailBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.jiondetail.IJionDetailView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class FreeDetailPresenter extends BasePresenter<IFreeDetailView> {

    public FreeDetailPresenter(Context context, IFreeDetailView iView) {
        super(context, iView);
    }

    public void getDetail(String start, String end, String shopId) {
        DialogUtils.showDialog(context, "加载中...");
        RetrofitHelper.getInstance()
                .getApi()
                .getCollectionDetail("22", start, end, shopId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        FreeDetailBean[] beens = new Gson().fromJson(stringModel.getResult(), FreeDetailBean[].class);
                        iView.success(Arrays.asList(beens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
