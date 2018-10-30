package com.admin.shopkeeper.entity;

import android.util.Log;

import com.admin.greendao.gen.DaoSession;
import com.admin.greendao.gen.FoodEntityDao;
import com.admin.greendao.gen.MenuTypeEntityDao;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guxiaogasumi on 2017/6/18.
 * {
 * "RESTAURANTID":"4b176f0e-0553-4094-8181-5048641b20ef",
 * "PRODUCTTYPEID":"7b5c6bbd-7346-466f-8a31-9e97e13244c2",
 * "PRODUCTTYPENAME":"凉菜和卤菜",
 * "ORDERNO":1
 * }
 */

@Entity
public class MenuTypeEntity implements Serializable, Comparable<MenuTypeEntity> {

    static final long serialVersionUID = 42L;
    @Id
    @SerializedName("PRODUCTTYPEID")
    private String productTypeID;

    @ToMany(referencedJoinProperty = "productTypeID")
    private List<FoodEntity> foods;


    @SerializedName("PRODUCTTYPENAME")
    private String productTypeName;

    @SerializedName("RESTAURANTID")
    private String restaurantID;

    @SerializedName("ORDERNO")
    private int orderNO;

    @SerializedName("Type")
    private boolean isType;

    @Transient
    private int count;

    boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Transient
    private boolean selected;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 211704465)
    private transient MenuTypeEntityDao myDao;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Generated(hash = 1148826198)
    public MenuTypeEntity(String productTypeID, String productTypeName, String restaurantID,
                          int orderNO, boolean isType, boolean check) {
        this.productTypeID = productTypeID;
        this.productTypeName = productTypeName;
        this.restaurantID = restaurantID;
        this.orderNO = orderNO;
        this.isType = isType;
        this.check = check;
    }

    @Generated(hash = 1529432203)
    public MenuTypeEntity() {
    }

    public String getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(String productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public int getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(int orderNO) {
        this.orderNO = orderNO;
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
    @Generated(hash = 1053153917)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMenuTypeEntityDao() : null;
    }


    public void setFoods(List<FoodEntity> foods) {
        this.foods = foods;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1950966)
    public synchronized void resetFoods() {
        foods = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1149176821)
    public List<FoodEntity> getFoods() {
        if (foods == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FoodEntityDao targetDao = daoSession.getFoodEntityDao();
            List<FoodEntity> foodsNew = targetDao._queryMenuTypeEntity_Foods(productTypeID);
            synchronized (this) {
                if (foods == null) {
                    foods = foodsNew;
                }
            }
        }

        return foods;
    }

    public boolean getIsType() {
        return this.isType;
    }

    public void setIsType(boolean isType) {
        this.isType = isType;
    }

    @Override
    public String toString() {
        return "MenuTypeEntity{" +
                "productTypeID='" + productTypeID + '\'' +
                ", productTypeName='" + productTypeName + '\'' +
                ", restaurantID='" + restaurantID + '\'' +
                ", orderNO=" + orderNO +
                ", isType=" + isType +
                ", count=" + count +
                ", selected=" + selected +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                '}';
    }

    public boolean getCheck() {
        return this.check;
    }

    @Override
    public int compareTo(MenuTypeEntity another) {
        if (another.getProductTypeName().equals("普通菜品")) {
            return 1;
        }
        if (getProductTypeName().equals("普通菜品")) {
            return -1;
        }
        return getProductTypeName().compareTo(another.getProductTypeName());
    }


}
