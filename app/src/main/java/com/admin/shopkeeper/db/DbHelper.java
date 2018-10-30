package com.admin.shopkeeper.db;


import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.KouWei;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.entity.Season;
import com.admin.shopkeeper.entity.Spec;
import com.admin.shopkeeper.entity.User;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by guxiaogasumi on 2017/6/13.
 */

public interface DbHelper {

    Observable<List<RoomEntity>> saveRooms(List<RoomEntity> list);

    Observable<List<MenuTypeEntity>> saveMenus(List<MenuTypeEntity> list);

    Observable<List<FoodEntity>> saveFoods(List<FoodEntity> list);
    Observable<List<Spec>> saveSpec(List<Spec> list);
    Observable<List<KouWei>> saveKouWei(List<KouWei> list);
    Observable<List<Season>> saveSeason(List<Season> list);
    Observable<List<RoomEntity>> getRooms(String id);
    Observable<List<MenuTypeEntity>> getMenuTypes(String id);
    Observable<List<MenuTypeEntity>> getMenuTypesFood(String id);
    Observable<List<MenuTypeEntity>> getMenuTypesMeal(String id);

    Observable<User> saveUser(User u);

    Observable<User> getUser();


}
