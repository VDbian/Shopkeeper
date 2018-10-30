package com.admin.shopkeeper.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/21.
 */
@Entity
public class Season implements Serializable, MultiItemEntity {
    static final long serialVersionUID = 42L;

    @Id
    @SerializedName("GUID")
    String guId;
    @SerializedName("ProtuctID")
    String protuctID;
    @SerializedName("Name")
    String name;
    @SerializedName("Price")
    double price;
    @SerializedName("RESTAURANTID")
    String restaurantId;
    @SerializedName("ProtuctName")
    String ProtuctName;
    @SerializedName("Type")
    boolean type;

    @Transient
    private boolean selected;
    @Transient
    private int count;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Generated(hash = 784390374)
    public Season(String guId, String protuctID, String name, double price, String restaurantId, String ProtuctName, boolean type) {
        this.guId = guId;
        this.protuctID = protuctID;
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
        this.ProtuctName = ProtuctName;
        this.type = type;
    }

    @Generated(hash = 1022390091)
    public Season() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getGuId() {
        return guId;
    }

    public void setGuId(String guId) {
        this.guId = guId;
    }

    public String getProtuctID() {
        return protuctID;
    }

    public void setProtuctID(String protuctID) {
        this.protuctID = protuctID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getProtuctName() {
        return ProtuctName;
    }

    public void setProtuctName(String protuctName) {
        ProtuctName = protuctName;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public boolean getType() {
        return this.type;
    }
}
