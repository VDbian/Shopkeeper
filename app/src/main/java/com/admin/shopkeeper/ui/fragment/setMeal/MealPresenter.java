package com.admin.shopkeeper.ui.fragment.setMeal;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.KouWei;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.Spec;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class MealPresenter extends BasePresenter<IMealView> {
    public MealPresenter(Context context, IMealView iView) {
        super(context, iView);
    }


    public void getMeal() {
        RetrofitHelper.getInstance().getApi().getMeal("2", App.INSTANCE().getShopID(), 1, 1000).compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(foodsModel -> {
                    if (foodsModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                        Timber.d("--->"+foodsModel.toString());
                        FoodEntity[] foodEntities = new Gson().fromJson(foodsModel.getFood(), FoodEntity[].class);
                        List<FoodEntity> foodEntityList = Arrays.asList(foodEntities);
                        MenuTypeEntity[] menuTypeEntities = new Gson().fromJson(foodsModel.getFoodType(), MenuTypeEntity[].class);
                        List<MenuTypeEntity> menuTypeEntityList = Arrays.asList(menuTypeEntities);
                        saveDB(foodEntityList, menuTypeEntityList);
                    } else {
                        iView.error(foodsModel.getMessage());
                    }
                }, throwable -> {
                    iView.warning("菜单列表获取失败");
                });
    }

    private void saveDB(List<FoodEntity> foodEntityList, List<MenuTypeEntity> menuTypeEntityList) {

        AppDbHelper.INSTANCE().deleteMeal(App.INSTANCE().getShopID());
        AppDbHelper.INSTANCE().saveMenus(menuTypeEntityList).subscribeOn(Schedulers.io()).subscribe();
        AppDbHelper.INSTANCE().saveFoods(foodEntityList).subscribeOn(Schedulers.io()).subscribe();

        AppDbHelper.INSTANCE().getMenuTypesMeal(App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().<List<MenuTypeEntity>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(menuTypeEntities -> iView.success(menuTypeEntities), Throwable::printStackTrace);


    }

    public void getDBFood() {
        AppDbHelper.INSTANCE().getMenuTypesMeal(App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().<List<MenuTypeEntity>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(menuTypeEntities -> {
                    if (menuTypeEntities.size() > 0) {
                        iView.success(menuTypeEntities);
                    }else {
                        iView.getService();
                    }
                }, Throwable::printStackTrace);
    }
}
