package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/19.
 */

public class ProductBean implements Serializable {
    @SerializedName("PRODUCTID")
    private String id;
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    @SerializedName("PRODUCTNAME")
    private String name;
    @SerializedName("Types")
    private String type;
    @SerializedName("Price")
    private double price;
    @SerializedName("Info")
    private String info;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
