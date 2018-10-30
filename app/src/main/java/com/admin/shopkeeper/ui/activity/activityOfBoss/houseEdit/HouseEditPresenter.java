package com.admin.shopkeeper.ui.activity.activityOfBoss.houseEdit;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.HouseBean;
import com.admin.shopkeeper.entity.RoomTypeBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.saleEdit.ISaleEditView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class HouseEditPresenter extends BasePresenter<IHouseEditView> {

    private Object areaData;

    public HouseEditPresenter(Context context, IHouseEditView iView) {
        super(context, iView);
    }

    public void commit(String id, String sortno, int state, String roomType, String area,
                       String price, String counts, String name) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .editHouse(TextUtils.isEmpty(id) ? "3" : "4", id, sortno,
                        state, App.INSTANCE().getShopID(), roomType, area, price, counts, name)
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

    public void getRoomData() {
        RetrofitHelper.getInstance()
                .getApi()
                .getHouse("5", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        RoomTypeBean[] printBeens = new Gson().fromJson(stringModel.getResult(), RoomTypeBean[].class);
                        iView.success(Arrays.asList(printBeens));
                    } else {
                        iView.error("获取房间失败");
                    }
                }, throwable -> {
                    iView.error("获取房间失败");
                });

    }

    public void getAreaData() {
        RetrofitHelper.getInstance()
                .getApi()
                .getHouse("6", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {

                    } else {
                        iView.error("获取区域失败");
                    }
                }, throwable -> {
                    iView.error("获取区域失败");
                });
    }
}
