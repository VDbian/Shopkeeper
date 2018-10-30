package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

import com.admin.greendao.gen.DaoSession;
import com.admin.greendao.gen.FoodEntityDao;
import com.admin.greendao.gen.SpecDao;

/**
 * Created by Administrator on 2017/6/28 0028.
 * {
 * "GUID":"4767cb26-19bd-41ba-9226-0373a58313d2",
 * "RESTAURANTID":"4b176f0e-0553-4094-8181-5048641b20ef",
 * "ProtuctID":"01444b32-fa76-484f-bcf6-f1a1c6489521",
 * "ProtuctName":"炝炒小白菜",
 * "Price":4,
 * "Name":"4"
 * }
 */
@Entity
public class Spec implements Serializable {
    static final long serialVersionUID = 42L;
    @Id
    @SerializedName("GUID")
    private String guId;

    @SerializedName("RESTAURANTID")
    private String restaurantID;

    @SerializedName("ProtuctID")
    private String protuctID;

    @ToOne(joinProperty = "protuctID")
    private FoodEntity food;

    @SerializedName("ProtuctName")
    private String protuctName;

    @SerializedName("Price")
    private double price;

    @SerializedName("Name")
    private String name;


    @Transient
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1688444317)
    private transient SpecDao myDao;

    @Generated(hash = 246391624)
    public Spec(String guId, String restaurantID, String protuctID,
                String protuctName, double price, String name) {
        this.guId = guId;
        this.restaurantID = restaurantID;
        this.protuctID = protuctID;
        this.protuctName = protuctName;
        this.price = price;
        this.name = name;
    }

    @Generated(hash = 689018142)
    public Spec() {
    }

    @Generated(hash = 718211672)
    private transient String food__resolvedKey;

    public String getGuId() {
        return guId;
    }

    public void setGuId(String guId) {
        this.guId = guId;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getProtuctID() {
        return protuctID;
    }

    public void setProtuctID(String protuctID) {
        this.protuctID = protuctID;
    }

    public String getProtuctName() {
        return protuctName;
    }

    public void setProtuctName(String protuctName) {
        this.protuctName = protuctName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1350669802)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSpecDao() : null;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1366503793)
    public FoodEntity getFood() {
        String __key = this.protuctID;
        if (food__resolvedKey == null || food__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FoodEntityDao targetDao = daoSession.getFoodEntityDao();
            FoodEntity foodNew = targetDao.load(__key);
            synchronized (this) {
                food = foodNew;
                food__resolvedKey = __key;
            }
        }
        return food;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 522145486)
    public void setFood(FoodEntity food) {
        synchronized (this) {
            this.food = food;
            protuctID = food == null ? null : food.getProductID();
            food__resolvedKey = protuctID;
        }
    }


}
