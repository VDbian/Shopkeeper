package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ShuxingBean implements Serializable{

    @SerializedName("GUID")
    private String guid;
    @SerializedName("ProtuctID")
    private String productId;
    @SerializedName("ProtuctName")
    private String productName;
    @SerializedName("Name")
    private String name;
    @SerializedName("Price")
    private double price;
    @SerializedName("RESTAURANTID")
    private String restaurantId;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
}
