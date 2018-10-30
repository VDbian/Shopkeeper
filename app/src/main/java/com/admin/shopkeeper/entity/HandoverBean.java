package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/26.
 */

public class HandoverBean implements Serializable {
    //{\"RESTAURANTID\":\"4B176F0E-0553-4094-8181-5048641B20EF\",
    // \"GUID\":\"c75ff800-6946-406b-84c6-87fe669eedaa\",
    // \"Names\":\"智慧餐厅\",
    // \"BTime\":\"2017-10-26 11:50:46\",
    // \"Time\":\"2017-10-26 11:50:46\",
    // \"Price\":932.00,
    // \"UserName\":\"收银\"}

    @SerializedName("RESTAURANTID")
    String shopId;
    @SerializedName("GUID")
    String guid;
    @SerializedName("Names")
    String names;
    @SerializedName("BTime")
    String bTime;
    @SerializedName("Time")
    String time;
    @SerializedName("Price")
    double price;
    @SerializedName("UserName")
    String username;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getbTime() {
        return bTime;
    }

    public void setbTime(String bTime) {
        this.bTime = bTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
