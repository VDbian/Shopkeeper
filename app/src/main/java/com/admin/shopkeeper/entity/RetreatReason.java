package com.admin.shopkeeper.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/16 0016.
 * {
 * "GUID":"ce502849-26c1-42e7-9f9b-637a149eca65",
 * "RESTAURANTID":"4b176f0e-0553-4094-8181-5048641b20ef",
 * "Remark":"太甜"
 * }
 */

public class RetreatReason implements MultiItemEntity {

    @SerializedName("GUID")
    private String guid;
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    @SerializedName("Remark")
    private String remark;
    private boolean selector;

    public boolean isSelector() {
        return selector;
    }

    public void setSelector(boolean selector) {
        this.selector = selector;
    }

    private int type;

    public static final int NORMAL = 0;

    public static final int EDIT = 1;

    public void setType(int type) {
        this.type = type;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
