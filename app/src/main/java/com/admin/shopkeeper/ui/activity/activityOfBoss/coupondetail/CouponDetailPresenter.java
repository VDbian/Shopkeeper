package com.admin.shopkeeper.ui.activity.activityOfBoss.coupondetail;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.GuizeBean;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.ShopBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.couponManage.ICouponManageView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/7.
 */

public class CouponDetailPresenter extends BasePresenter<ICouponDetailView> {

    public CouponDetailPresenter(Context context, ICouponDetailView iView) {
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
                        iView.deleteSuccess("删除成功");
                    } else {
                        iView.error("删除失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("删除失败");
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

    public void setShopData(CouponManageBean bean, String value) {
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

    public void getShopDetail(CouponManageBean bean) {
        RetrofitHelper.getInstance()
                .getApi()
                .getshopdetail("6", bean.getPiCi())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        ShopBean[] shopBeen = new Gson().fromJson(stringModel.getResult(), ShopBean[].class);
                        iView.shopdetail(Arrays.asList(shopBeen));
                    }
                }, throwable -> {
                });
    }

    public void getDetail(CouponManageBean bean) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getCouponDetail("8", bean.getPiCi(), App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        String[] split = stringModel.getResult().split("\\^");
//                        if (split.length < 2) {
//                            iView.error("加载失败");
//                            return;
//                        }
                        GuizeBean[] beans = new Gson().fromJson(split[1], GuizeBean[].class);
                        if (beans != null && beans.length > 0) {
                            iView.showDetail(Arrays.asList(beans));
                        }
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    throwable.printStackTrace();
                    iView.error("加载失败");
                });
    }
}
