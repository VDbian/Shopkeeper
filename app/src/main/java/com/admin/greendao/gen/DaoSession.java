package com.admin.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.KouWei;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.entity.Season;
import com.admin.shopkeeper.entity.Spec;
import com.admin.shopkeeper.entity.User;

import com.admin.greendao.gen.FoodEntityDao;
import com.admin.greendao.gen.KouWeiDao;
import com.admin.greendao.gen.MenuTypeEntityDao;
import com.admin.greendao.gen.RoomEntityDao;
import com.admin.greendao.gen.SeasonDao;
import com.admin.greendao.gen.SpecDao;
import com.admin.greendao.gen.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig foodEntityDaoConfig;
    private final DaoConfig kouWeiDaoConfig;
    private final DaoConfig menuTypeEntityDaoConfig;
    private final DaoConfig roomEntityDaoConfig;
    private final DaoConfig seasonDaoConfig;
    private final DaoConfig specDaoConfig;
    private final DaoConfig userDaoConfig;

    private final FoodEntityDao foodEntityDao;
    private final KouWeiDao kouWeiDao;
    private final MenuTypeEntityDao menuTypeEntityDao;
    private final RoomEntityDao roomEntityDao;
    private final SeasonDao seasonDao;
    private final SpecDao specDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        foodEntityDaoConfig = daoConfigMap.get(FoodEntityDao.class).clone();
        foodEntityDaoConfig.initIdentityScope(type);

        kouWeiDaoConfig = daoConfigMap.get(KouWeiDao.class).clone();
        kouWeiDaoConfig.initIdentityScope(type);

        menuTypeEntityDaoConfig = daoConfigMap.get(MenuTypeEntityDao.class).clone();
        menuTypeEntityDaoConfig.initIdentityScope(type);

        roomEntityDaoConfig = daoConfigMap.get(RoomEntityDao.class).clone();
        roomEntityDaoConfig.initIdentityScope(type);

        seasonDaoConfig = daoConfigMap.get(SeasonDao.class).clone();
        seasonDaoConfig.initIdentityScope(type);

        specDaoConfig = daoConfigMap.get(SpecDao.class).clone();
        specDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        foodEntityDao = new FoodEntityDao(foodEntityDaoConfig, this);
        kouWeiDao = new KouWeiDao(kouWeiDaoConfig, this);
        menuTypeEntityDao = new MenuTypeEntityDao(menuTypeEntityDaoConfig, this);
        roomEntityDao = new RoomEntityDao(roomEntityDaoConfig, this);
        seasonDao = new SeasonDao(seasonDaoConfig, this);
        specDao = new SpecDao(specDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(FoodEntity.class, foodEntityDao);
        registerDao(KouWei.class, kouWeiDao);
        registerDao(MenuTypeEntity.class, menuTypeEntityDao);
        registerDao(RoomEntity.class, roomEntityDao);
        registerDao(Season.class, seasonDao);
        registerDao(Spec.class, specDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        foodEntityDaoConfig.clearIdentityScope();
        kouWeiDaoConfig.clearIdentityScope();
        menuTypeEntityDaoConfig.clearIdentityScope();
        roomEntityDaoConfig.clearIdentityScope();
        seasonDaoConfig.clearIdentityScope();
        specDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public FoodEntityDao getFoodEntityDao() {
        return foodEntityDao;
    }

    public KouWeiDao getKouWeiDao() {
        return kouWeiDao;
    }

    public MenuTypeEntityDao getMenuTypeEntityDao() {
        return menuTypeEntityDao;
    }

    public RoomEntityDao getRoomEntityDao() {
        return roomEntityDao;
    }

    public SeasonDao getSeasonDao() {
        return seasonDao;
    }

    public SpecDao getSpecDao() {
        return specDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}