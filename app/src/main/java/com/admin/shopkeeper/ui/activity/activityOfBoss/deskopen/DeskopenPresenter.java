package com.admin.shopkeeper.ui.activity.activityOfBoss.deskopen;

import android.content.Context;

import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.DeskOpenBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class DeskopenPresenter extends BasePresenter<IDeskopenView> {

    public DeskopenPresenter(Context context, IDeskopenView iView) {
        super(context, iView);
    }

    public void getData(String startDate, String endDate, String startTime, String endTime, int selectType, String shopId) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getJion("5", 100, 1, "ASC", startDate, endDate, startTime, endTime, shopId, selectType)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("")) {
                            iView.error("查询数据为空");
                            iView.success(new ArrayList<>());
                        } else {
                            DeskOpenBean[] beens = new Gson().fromJson(stringModel.getResult(), DeskOpenBean[].class);
                            iView.success(Arrays.asList(beens));
                        }
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

}
