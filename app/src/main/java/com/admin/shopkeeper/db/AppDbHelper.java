package com.admin.shopkeeper.db;



import com.admin.greendao.gen.DaoSession;
import com.admin.greendao.gen.FoodEntityDao;
import com.admin.greendao.gen.KouWeiDao;
import com.admin.greendao.gen.MenuTypeEntityDao;
import com.admin.greendao.gen.RoomEntityDao;
import com.admin.greendao.gen.SeasonDao;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.KouWei;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.entity.Season;
import com.admin.shopkeeper.entity.Spec;
import com.admin.shopkeeper.entity.User;
import com.admin.shopkeeper.utils.SPUtils;


import java.util.List;

import io.reactivex.Observable;

public class AppDbHelper implements DbHelper {


    public static AppDbHelper INSTANCE() {
        return new AppDbHelper();
    }

    private final DaoSession mDaoSession;

    private AppDbHelper() {
        mDaoSession = DaoManager.getInstance().getmDaoSession();
    }


    @Override
    public Observable<List<RoomEntity>> saveRooms(List<RoomEntity> list) {
        mDaoSession.clear();
        return Observable.fromCallable(() -> {
            mDaoSession.getRoomEntityDao().deleteAll();
            mDaoSession.getRoomEntityDao().insertOrReplaceInTx(list);
            return list;
        });
    }

    @Override
    public Observable<List<MenuTypeEntity>> saveMenus(List<MenuTypeEntity> list) {
        mDaoSession.clear();
        return Observable.fromCallable(() -> {
            mDaoSession.getMenuTypeEntityDao().insertOrReplaceInTx(list);
            return list;
        });
    }

    @Override
    public Observable<List<FoodEntity>> saveFoods(List<FoodEntity> list) {
        mDaoSession.clear();
        return Observable.fromCallable(() -> {
            mDaoSession.getFoodEntityDao().insertOrReplaceInTx(list);
            return list;
        });
    }

    @Override
    public Observable<List<Spec>> saveSpec(List<Spec> list) {
        mDaoSession.clear();
        return Observable.fromCallable(() -> {
            mDaoSession.getSpecDao().insertOrReplaceInTx(list);
            return list;
        });
    }

    @Override
    public Observable<List<KouWei>> saveKouWei(List<KouWei> list) {
        mDaoSession.clear();
        return Observable.fromCallable(() -> {
            mDaoSession.getKouWeiDao().insertOrReplaceInTx(list);
            return list;
        });
    }

    @Override
    public Observable<List<Season>> saveSeason(List<Season> list) {
        mDaoSession.clear();
        return Observable.fromCallable(() -> {
            mDaoSession.getSeasonDao().insertOrReplaceInTx(list);
            return list;
        });
    }

    public void deleteAll() {
        mDaoSession.clear();
        mDaoSession.getMenuTypeEntityDao().deleteAll();
        mDaoSession.getFoodEntityDao().deleteAll();
        mDaoSession.getSpecDao().deleteAll();
        mDaoSession.getKouWeiDao().deleteAll();
        mDaoSession.getSeasonDao().deleteAll();
    }


    @Override
    public Observable<List<RoomEntity>> getRooms(String id) {
        mDaoSession.clear();
        return Observable.fromCallable(() -> mDaoSession
                .getRoomEntityDao()
                .queryBuilder().where(RoomEntityDao.Properties.RestaurantId.like(id)).list());
    }


    @Override
    public Observable<List<MenuTypeEntity>> getMenuTypes(String id) {
        mDaoSession.clear();
        return Observable.fromCallable(() -> mDaoSession
                .getMenuTypeEntityDao()
                .queryBuilder()
                .where(MenuTypeEntityDao.Properties.RestaurantID.like(id))
                .list());
    }

    @Override
    public Observable<List<MenuTypeEntity>> getMenuTypesFood(String id) {
        mDaoSession.clear();
        return Observable.fromCallable(() -> mDaoSession
                .getMenuTypeEntityDao()
                .queryBuilder()
                .where(MenuTypeEntityDao.Properties.RestaurantID.like(id))
                .orderAsc(MenuTypeEntityDao.Properties.OrderNO)
                .list());
    }

    @Override
    public Observable<List<MenuTypeEntity>> getMenuTypesMeal(String id) {
        mDaoSession.clear();
        return Observable.fromCallable(() -> mDaoSession
                .getMenuTypeEntityDao()
                .queryBuilder()
                .where(MenuTypeEntityDao.Properties.RestaurantID.like(id), MenuTypeEntityDao.Properties.IsType.eq(true))
                .orderAsc(MenuTypeEntityDao.Properties.OrderNO).list());
    }



    @Override
    public Observable<User> saveUser(User u) {

        return Observable.fromCallable(() -> {
            mDaoSession.getUserDao().insertOrReplace(u);
            return u;
        });
    }

    @Override
    public Observable<User> getUser() {
        return Observable.fromCallable(() ->
                mDaoSession.getUserDao().load(SPUtils.getInstance().getString(SPUtils.PREFERENCE_USER)));
    }

    public User getiUser() {
        return mDaoSession.getUserDao().load(SPUtils.getInstance().getString(SPUtils.PREFERENCE_USER));
    }

    public boolean getMenuCount(String id) {
        return mDaoSession
                .getMenuTypeEntityDao()
                .queryBuilder().where(MenuTypeEntityDao.Properties.RestaurantID.like(id)).count() > 0;
    }

    public boolean getMenuFoodCount(String id) {
        return mDaoSession
                .getMenuTypeEntityDao()
                .queryBuilder().where(MenuTypeEntityDao.Properties.RestaurantID.like(id),
                        MenuTypeEntityDao.Properties.IsType.eq(false)).count() > 0;
    }

    public boolean getMenuMealCount(String id) {
        return mDaoSession
                .getMenuTypeEntityDao()
                .queryBuilder().where(MenuTypeEntityDao.Properties.RestaurantID.like(id), MenuTypeEntityDao.Properties.IsType.eq(true))
                .orderAsc(MenuTypeEntityDao.Properties.OrderNO).count() > 0;
    }

    public boolean getRoomCount(String id) {
        return mDaoSession
                .getRoomEntityDao()
                .queryBuilder()
                .where(RoomEntityDao.Properties.RestaurantId.like(id))
                .count() > 0;
    }

    public List<KouWei> getKouWeis(String id, String productId) {
        mDaoSession.clear();
        return mDaoSession
                .getKouWeiDao()
                .queryBuilder()
                .where(KouWeiDao.Properties.RestaurantId.like(id))
                .whereOr(KouWeiDao.Properties.PatientId.like(productId), KouWeiDao.Properties.PatientId.isNull())
                .orderAsc(KouWeiDao.Properties.No)
                .list();
    }

    public List<KouWei> getKouWeis(String id) {
        mDaoSession.clear();
        return mDaoSession
                .getKouWeiDao()
                .queryBuilder()
                .where(KouWeiDao.Properties.RestaurantId.like(id))
                .where(KouWeiDao.Properties.Own.eq(false))
                .orderAsc(KouWeiDao.Properties.No)
                .list();
    }

    public List<KouWei> getKouWeis(String id, boolean own) {
        mDaoSession.clear();
        return mDaoSession
                .getKouWeiDao()
                .queryBuilder().where(KouWeiDao.Properties.RestaurantId.like(id), KouWeiDao.Properties.Own.eq(own))
                .orderAsc(KouWeiDao.Properties.No)
                .list();
    }

    public List<Season> getSeason(String id, String productId) {
        mDaoSession.clear();
        return mDaoSession
                .getSeasonDao()
                .queryBuilder().where(SeasonDao.Properties.RestaurantId.like(id), SeasonDao.Properties.ProtuctID.like(productId), SeasonDao.Properties.Type.eq(true))
                .list();
    }

    public List<KouWei> getOwnKouWeis(String id, String foodId) {
        mDaoSession.clear();
        return mDaoSession
                .getKouWeiDao()
                .queryBuilder()
                .where(KouWeiDao.Properties.RestaurantId.like(id), KouWeiDao.Properties.PatientId.eq(foodId), KouWeiDao.Properties.Own.eq(true))
                .list();
    }

    public Observable<List<FoodEntity>> queryFoods(String text) {
        mDaoSession.clear();
        return Observable.fromCallable(() -> mDaoSession
                .getFoodEntityDao()
                .queryBuilder()
                .whereOr(FoodEntityDao.Properties.ProductName.like("%" + text + "%"),FoodEntityDao.Properties.Id.like("%" + text + "%"),
                        FoodEntityDao.Properties.PinYin.like("%" + text + "%")).orderAsc(FoodEntityDao.Properties.PinYin).list());
    }

    public FoodEntity queryFood(String foodId) {
        mDaoSession.clear();
        List<FoodEntity> list = mDaoSession.getFoodEntityDao()
                .queryBuilder()
                .where(FoodEntityDao.Properties.ProductID.like(foodId))
                .list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public void pushFood(int status, String foodId) {
        FoodEntity foodEntity = queryFood(foodId);
        foodEntity.setState(status);
        mDaoSession.getFoodEntityDao().insertOrReplace(foodEntity);
    }

    public void pushAllFood() {
        List<FoodEntity> list = mDaoSession.getFoodEntityDao()
                .queryBuilder()
                .list();
        if (list != null && list.size() > 0) {
            for (FoodEntity foodEntity : list) {
                foodEntity.setState(1);
                mDaoSession.getFoodEntityDao().insertOrReplace(foodEntity);
            }
        }
    }

    public void deleteRoom(String shopID) {
        mDaoSession.getRoomEntityDao().deleteInTx(mDaoSession
                .getRoomEntityDao()
                .queryBuilder().where(RoomEntityDao.Properties.RestaurantId.like(shopID)).list());
    }


    public void deleteMenu(String shopID) {
        mDaoSession
                .getMenuTypeEntityDao()
                .queryBuilder()
                .where(MenuTypeEntityDao.Properties.RestaurantID.like(shopID), MenuTypeEntityDao.Properties.IsType.eq(false))
                .orderAsc(MenuTypeEntityDao.Properties.OrderNO).list();
    }

    public void deleteMeal(String shopID) {
        Observable.fromCallable(() -> mDaoSession
                .getMenuTypeEntityDao()
                .queryBuilder().where(MenuTypeEntityDao.Properties.RestaurantID.like(shopID), MenuTypeEntityDao.Properties.IsType.eq(true))
                .orderAsc(MenuTypeEntityDao.Properties.OrderNO).list());
    }
}

