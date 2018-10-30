package com.admin.shopkeeper.ui.activity.activityOfBoss.salestatistics;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.shopcollection.IShopCollectionView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class SaleStatisticsPresenter extends BasePresenter<ISaleStatisticsView> {

    public SaleStatisticsPresenter(Context context, ISaleStatisticsView iView) {
        super(context, iView);
    }

    public void getData(String startDate, String endDate, String startTime, String endTime,
                        int selectType, String productId,String productTypeId,String shopId) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getSale("6", 100, 1, "ASC", startDate, endDate, startTime, endTime,
                        shopId, productId,productTypeId, selectType)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        String[] split = stringModel.getResult().split("\\^");
                        SaleStatisticsBean[] beens = new Gson().fromJson(split[0], SaleStatisticsBean[].class);
                        SaleStatisticsBean[] totle = new Gson().fromJson(split[1], SaleStatisticsBean[].class);
                        iView.success(Arrays.asList(beens));
                        iView.totle(Arrays.asList(totle));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void getGoods() {
        RetrofitHelper.getInstance()
                .getApi()
                .getChain("14", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        FoodEntity[] beens = new Gson().fromJson(stringModel.getResult(), FoodEntity[].class);
                        iView.foodSuccess(Arrays.asList(beens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    iView.error("加载失败");
                });
    }
}
