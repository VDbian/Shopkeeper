package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/9 0009.
 * {
 * "Guid":"7867d0f6-fd76-4f67-ad54-0f53ed244a21",
 * "Name":"餐具费",
 * "Price":1,
 * "Remark":"",
 * "RESTAURANTID":"020B37AD-12D4-4A8A-87D4-5DC04FF1892D",
 * "Time":"2016-10-18 22:46:10"
 * }
 */

public class CanJu implements Serializable{

    @SerializedName("Guid")
    private String guid;
    @SerializedName("Name")
    private String name;
    @SerializedName("Price")
    private double price;
    @SerializedName("Remark")
    private String remark;
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    @SerializedName("Time")
    private String time;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
