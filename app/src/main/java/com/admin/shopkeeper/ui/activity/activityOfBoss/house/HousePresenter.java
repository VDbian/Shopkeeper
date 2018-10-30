package com.admin.shopkeeper.ui.activity.activityOfBoss.house;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.HouseBean;
import com.admin.shopkeeper.entity.PrintBean;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.print.IPrintManagerView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class HousePresenter extends BasePresenter<IHouseView> {

    public HousePresenter(Context context, IHouseView iView) {
        super(context, iView);
    }

    public void getData() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getHouse("1", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        HouseBean[] printBeens = new Gson().fromJson(stringModel.getResult(), HouseBean[].class);
                        iView.success(Arrays.asList(printBeens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });

    }

    public void delete(HouseBean bean) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .deleteHouse("2", bean.getId())
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
