package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/26.
 */

public class HandoverDetailBean implements Serializable {

    //{\"GUID\":\"9a17540d-90c0-4c2d-b7ed-404458d1fba1\",
    // \"SuccessID\":\"c75ff800-6946-406b-84c6-87fe669eedaa\",
    // \"PayName\":\"被扫支付宝\",
    // \"PayType\":\"6\",
    // \"RESTAURANTID\":\"4B176F0E-0553-4094-8181-5048641B20EF\",
    // \"Price\":12.00,\"Counts\":1}

    @SerializedName("GUID")
    String guid;
    @SerializedName("SuccessID")
    String successId;
    @SerializedName("PayName")
    String payName;
    @SerializedName("PayType")
    String payType;
    @SerializedName("RESTAURANTID")
    String shopId;
    @SerializedName("Price")
    double price;
    @SerializedName("Counts")
    int counts;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSuccessId() {
        return successId;
    }

    public void setSuccessId(String successId) {
        this.successId = successId;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
}
