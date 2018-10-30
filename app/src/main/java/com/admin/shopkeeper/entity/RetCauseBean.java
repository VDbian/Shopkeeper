package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/30.
 */

public class RetCauseBean implements Serializable {

    //{\"GUID\":\"72536273-8cd7-4048-a87c-2b82bf637ab7\",
    // \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
    // \"Remark\":\"顾客点错\"}
    @SerializedName("GUID")
    String guid;
    @SerializedName("RESTAURANTID")
    String restaurantId;
    @SerializedName("Remark")
    String remark;

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
    public String toString() {
        return "RetCauseBean{" +
                "guid='" + guid + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
