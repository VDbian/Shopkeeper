package com.admin.shopkeeper.ui.activity.activityOfBoss.couponManage;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.MemberLevelManageBean;
import com.admin.shopkeeper.entity.ShopBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/7.
 */

public class CouponManagePresenter extends BasePresenter<ICouponManageView> {

    public CouponManagePresenter(Context context, ICouponManageView iView) {
        super(context, iView);
    }

    public void deleteCoupon(String piCi) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .deleteCoupon("3", piCi)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("删除成功");
                        //getCouponInfo();
                    } else {
                        iView.error("删除失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("删除失败");
                });

    }

    public void getCouponInfo(int page, String leiBie) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getCouponInfo("9", 20, page, "ASC", "", leiBie, App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        CouponManageBean[] couponManageBeen = new Gson().fromJson(stringModel.getResult(), CouponManageBean[].class);
                        iView.success(Arrays.asList(couponManageBeen));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void getGroupCouponInfo() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getGroupCouponInfo("1", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        CouponManageBean[] couponManageBeen = new Gson().fromJson(stringModel.getResult(), CouponManageBean[].class);
                        iView.success(Arrays.asList(couponManageBeen));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void getLineDownInfo(int index) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getLineDownInfo("1", App.INSTANCE().getShopID(),20 ,index,"")
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        CouponManageBean[] couponManageBeen = new Gson().fromJson(stringModel.getResult(), CouponManageBean[].class);
                        iView.success(Arrays.asList(couponManageBeen));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void getRechargeInfo() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getRechargeInfo("1", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
//                        CouponManageBean[] couponManageBeen = new Gson().fromJson(stringModel.getResult(), CouponManageBean[].class);
//                        iView.success(Arrays.asList(couponManageBeen));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void getShopData(CouponManageBean bean) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getShopInfo("4", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        ShopBean[] shopBeen = new Gson().fromJson(stringModel.getResult(), ShopBean[].class);
                        iView.shopsuccess(bean, Arrays.asList(shopBeen));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void setShopData1(CouponManageBean bean, String value) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .setShop1("5", App.INSTANCE().getShopID(), bean.getPiCi(), value)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("设置成功");
                    } else {
                        iView.error("设置失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("设置失败");
                });
    }

}
