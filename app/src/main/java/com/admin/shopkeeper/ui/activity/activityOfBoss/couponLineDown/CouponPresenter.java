package com.admin.shopkeeper.ui.activity.activityOfBoss.couponLineDown;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.MansongBean;
import com.admin.shopkeeper.entity.ShopBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class CouponPresenter extends BasePresenter<ICouponLineDownView> {

    public CouponPresenter(Context context, ICouponLineDownView iView) {
        super(context, iView);
    }

    public void getLineDownInfo(int index , String productName) {
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
                        CouponLineDownBean[] couponLineDownBeen = new Gson().fromJson(stringModel.getResult(), CouponLineDownBean[].class);
                        iView.success(Arrays.asList(couponLineDownBeen));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
    public void delete(String guid) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .deleteCouponLine("4", guid)
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
   /* public void getShopData(CouponLineDownBean bean) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getShopOfCouponLine("6", bean.getGuid())
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
    }*/
}
