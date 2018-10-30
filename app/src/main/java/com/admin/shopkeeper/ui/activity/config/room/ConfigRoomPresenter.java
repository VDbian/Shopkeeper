package com.admin.shopkeeper.ui.activity.config.room;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;

import com.admin.shopkeeper.utils.SPUtils;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by guxiaogasumi on 2017/6/17.
 */

public class ConfigRoomPresenter extends BasePresenter<IConfigRoomView> {

    public ConfigRoomPresenter(Context context, IConfigRoomView iView) {
        super(context, iView);
    }

    public void getDB() {
        AppDbHelper.INSTANCE().getRooms(App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().<List<RoomEntity>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(roomEntities -> {
                    if (roomEntities.size() > 0) {
                        iView.showRooms(roomEntities);
                    } else {
                        iView.noDataWithDB();
                    }
                }, throwable -> iView.noDataWithDB());
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

    private void saveDB(List<RoomEntity> list) {
        AppDbHelper.INSTANCE().deleteRoom(App.INSTANCE().getShopID());
        AppDbHelper.INSTANCE().saveRooms(list).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(menuTypeEntities -> {
                    iView.showRooms(menuTypeEntities);
                }, Throwable::printStackTrace);
    }

}
