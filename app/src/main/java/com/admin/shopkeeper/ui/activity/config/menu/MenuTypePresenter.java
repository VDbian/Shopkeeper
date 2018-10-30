package com.admin.shopkeeper.ui.activity.config.menu;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.ui.activity.config.menu.IMenuTypeView;
import com.admin.shopkeeper.utils.SPUtils;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by guxiaogasumi on 2017/6/18.
 */

class MenuTypePresenter extends BasePresenter<IMenuTypeView> {
    MenuTypePresenter(Context context, IMenuTypeView iView) {
        super(context, iView);
    }

    void getFoodsTypes() {

        RetrofitHelper.getInstance().getApi()
                .getFoodsTypes(App.INSTANCE().getShopID(), "1", 1, 1000)
                .compose(getActivityLifecycleProvider().<StringModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                        MenuTypeEntity[] entities = new Gson().fromJson(stringModel.getResult(), MenuTypeEntity[].class);
                        List<MenuTypeEntity> list = Arrays.asList(entities);
                        if (list.size() > 0) {
                            saveDB(list);
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

    private void saveDB(List<MenuTypeEntity> list) {
        AppDbHelper.INSTANCE().saveMenus(list).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(menuTypeEntities -> {
                    iView.showMenus(menuTypeEntities);
                }, Throwable::printStackTrace);
    }

    void getDB() {
        AppDbHelper.INSTANCE().getMenuTypes(App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().<List<MenuTypeEntity>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(menuTypeEntities -> {
                    if (menuTypeEntities.size() > 0) {
                        iView.showMenus(menuTypeEntities);
                    } else {
                        iView.noDataWithDB();
                    }
                }, throwable -> iView.noDataWithDB());
    }
}
