package com.admin.shopkeeper.ui.activity.home;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.KouWei;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.entity.Season;
import com.admin.shopkeeper.entity.Spec;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.FoodsModel;
import com.admin.shopkeeper.model.StringModel;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class HomePresenter extends BasePresenter<IHomeView> {
    public HomePresenter(Context context, IHomeView iView) {
        super(context, iView);
    }

    public void getServers() {
        RetrofitHelper.getInstance().getApi().getRooms("1", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().<StringModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                        RoomEntity[] entities = new Gson().fromJson(stringModel.getResult(), RoomEntity[].class);
                        List<RoomEntity> list = Arrays.asList(entities);
                        if (list.size() > 0) {
                            saveRoomDB(list);
                        } else {
                            iView.warning("菜单类型列表为空");
                        }
                    } else {
                        iView.error(stringModel.getMessage());
                    }
                }, throwable -> {
                    iView.error("获取菜单列表失败");
                });
    }

    private void saveRoomDB(List<RoomEntity> list) {
        AppDbHelper.INSTANCE().deleteRoom(App.INSTANCE().getShopID());
        AppDbHelper.INSTANCE().saveRooms(list).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(menuTypeEntities -> {
                    iView.showRooms(menuTypeEntities);
                }, Throwable::printStackTrace);
    }

    public void getFoods() {
        RetrofitHelper.getInstance().getApi()
                .getFoods("0", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().<FoodsModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(foodsModel -> {
                    if (foodsModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                        FoodEntity[] foodEntities = new Gson().fromJson(foodsModel.getFood(), FoodEntity[].class);
                        List<FoodEntity> foodEntityList = Arrays.asList(foodEntities);
                        MenuTypeEntity[] menuTypeEntities = new Gson().fromJson(foodsModel.getFoodType(), MenuTypeEntity[].class);
                        List<MenuTypeEntity> menuTypeEntityList = Arrays.asList(menuTypeEntities);
                        KouWei[] kouWeis = new Gson().fromJson(foodsModel.getKouWei(), KouWei[].class);
                        List<KouWei> kouWeiList = Arrays.asList(kouWeis);

                        Season[] seasons = new Gson().fromJson(foodsModel.getSeason(), Season[].class);
                        List<Season> seasonList = Arrays.asList(seasons);

                        KouWei[] ProductKouWeis = new Gson().fromJson(foodsModel.getProductKouWei(), KouWei[].class);
                        List<KouWei> productKouWeiList = Arrays.asList(ProductKouWeis);

                        Spec[] specs = new Gson().fromJson(foodsModel.getSpec(), Spec[].class);
                        List<Spec> specList = Arrays.asList(specs);
                        saveDB(foodEntityList, menuTypeEntityList, kouWeiList,seasonList, productKouWeiList, specList);
                    } else {
                        iView.warning(foodsModel.getMessage());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    iView.warning("菜单列表获取失败");
                });
    }

    public void getMeal() {
        RetrofitHelper.getInstance()
                .getApi()
                .getMeal("2", App.INSTANCE().getShopID(), 1, 1000)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
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
                    throwable.printStackTrace();
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
                .subscribe(menuTypeEntities -> {
                    iView.success(menuTypeEntities);
                },throwable -> {
                    throwable.printStackTrace();
                });
                //.subscribe(menuTypeEntities -> iView.success(menuTypeEntities), Throwable::printStackTrace);
    }

    private void saveDB(List<FoodEntity> foodEntityList, List<MenuTypeEntity> menuTypeEntityList, List<KouWei> kouWeiList,List<Season> seasonList, List<KouWei> productKouWeiList, List<Spec> specList) {
        AppDbHelper.INSTANCE().deleteMenu(App.INSTANCE().getShopID());
        AppDbHelper.INSTANCE().saveMenus(menuTypeEntityList).subscribeOn(Schedulers.io()).subscribe();
        AppDbHelper.INSTANCE().saveFoods(foodEntityList).subscribeOn(Schedulers.io()).subscribe();
        AppDbHelper.INSTANCE().saveSpec(specList).subscribeOn(Schedulers.io()).subscribe();
        AppDbHelper.INSTANCE().saveKouWei(kouWeiList).subscribeOn(Schedulers.io()).subscribe();
        AppDbHelper.INSTANCE().saveSeason(seasonList).subscribeOn(Schedulers.io()).subscribe();
        AppDbHelper.INSTANCE().saveKouWei(productKouWeiList).subscribeOn(Schedulers.io()).subscribe();
        AppDbHelper.INSTANCE().getMenuTypesFood(App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().<List<MenuTypeEntity>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(menuTypeEntities -> {
                    //iView.success(menuTypeEntities);
                    getMeal();
                }, Throwable::printStackTrace);
    }
}
