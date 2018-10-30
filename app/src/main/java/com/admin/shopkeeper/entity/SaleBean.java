package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/6.
 */

public class SaleBean implements Serializable {

    @SerializedName("Guid")
    String guiId;
    @SerializedName("RESTAURANTID")
    String restaurantId;
    @SerializedName("Count")
    String count;
    @SerializedName("Name")
    String name;

    public String getGuiId() {
        return guiId;
    }

    public void setGuiId(String guiId) {
        this.guiId = guiId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
