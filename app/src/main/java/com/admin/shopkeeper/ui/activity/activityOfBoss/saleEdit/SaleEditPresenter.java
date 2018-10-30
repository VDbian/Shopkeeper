package com.admin.shopkeeper.ui.activity.activityOfBoss.saleEdit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.KouWei;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.entity.SaleBean;
import com.admin.shopkeeper.entity.Season;
import com.admin.shopkeeper.entity.Spec;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.FoodsModel;
import com.admin.shopkeeper.ui.activity.activityOfBoss.sale.ISaleView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Administrator on 2017/8/24.
 */

public class SaleEditPresenter extends BasePresenter<ISaleEditView> {

    public SaleEditPresenter(Context context, ISaleEditView iView) {
        super(context, iView);
    }

    public void commit(String guid, String nameStr, String countStr, String ptypeid, String pid1) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .addSale(TextUtils.isEmpty(guid) ? "2" : "3", countStr, nameStr, guid, ptypeid, pid1, App.INSTANCE().getShopID())
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

    public void getFoodType() {
        RetrofitHelper.getInstance().getApi()
                .getFoods("0", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().<FoodsModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(foodsModel -> {
                    if (foodsModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                        MenuTypeEntity[] menuTypeEntities = new Gson().fromJson(foodsModel.getFoodType(), MenuTypeEntity[].class);
                        iView.getFoodTypeSuccess(Arrays.asList(menuTypeEntities));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    iView.error("加载失败");
                });
    }

    public void getFood() {
        RetrofitHelper.getInstance()
                .getApi()
                .getFoodsList("1", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        FoodBean[] foods = new Gson().fromJson(stringModel.getResult(), FoodBean[].class);
                        iView.getFoodSuccess(Arrays.asList(foods));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    iView.error("加载失败");
                });

    }

}
