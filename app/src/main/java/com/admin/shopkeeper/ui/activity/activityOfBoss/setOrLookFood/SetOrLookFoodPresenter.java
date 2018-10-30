package com.admin.shopkeeper.ui.activity.activityOfBoss.setOrLookFood;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.FindFoodCouponDownBean;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.FoodsModel;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class SetOrLookFoodPresenter extends BasePresenter<ISetOrLookFoodView> {

    public SetOrLookFoodPresenter(Context context, ISetOrLookFoodView iView) {
        super(context, iView);
    }

    public void getSetFood(int pageIndex ,String productName , String couponId) {
        RetrofitHelper.getInstance()
                .getApi()
                .getSetFood("7", 20 , pageIndex ,productName,couponId ,App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("0")) {
                            iView.error("加载失败");
                        } else {
                            FindFoodCouponDownBean[] been = new Gson().fromJson(stringModel.getResult(), FindFoodCouponDownBean[].class);
                            iView.success(Arrays.asList(been));
                        }
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    throwable.printStackTrace();
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


    public void save(String productId, String productTypeId, String couponId) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .saveFoodAndType("6", App.INSTANCE().getShopID(), productTypeId, productId, couponId)
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
