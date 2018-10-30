package com.admin.shopkeeper.db;

import com.admin.greendao.gen.DaoMaster;
import com.admin.greendao.gen.DaoSession;
import com.admin.shopkeeper.App;

public class DaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DaoManager() {
        init();
    }

    /**
     * 静态内部类，实例化对象使用
     */
    private static class SingleInstanceHolder {
        private static final DaoManager INSTANCE = new DaoManager();
    }

    /**
     * 对外唯一实例的接口
     *
     * @return
     */
    public static DaoManager getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }

    /**
     * 初始化数据
     */
    private void init() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(App.getAPPContext(),
                "shopkeeper");
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}