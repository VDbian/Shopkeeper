package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/27.
 */

public class GiftStatisticsBean implements Serializable {
    //{\"PRODUCTNAME\":\"凤 尾\",\"Giving\":1,\"PRICE\":10.00}
    @SerializedName("PRODUCTNAME")
    String productName;
    @SerializedName("Giving")
    int giving;
    @SerializedName("PRICE")
    double price;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getGiving() {
        return giving;
    }

    public void setGiving(int giving) {
        this.giving = giving;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
