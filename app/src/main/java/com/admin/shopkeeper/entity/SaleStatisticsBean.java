package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/26.
 */

public class SaleStatisticsBean implements Serializable {
    //{\"PRODUCTNAME\":\"木须肉\",
    // \"PRODUCTID\":\"0fe10ae8-7c99-4a5b-87d2-2e68a3dd6e69\",
    // \"ID\":\"12\",
    // \"PRODUCTTYPENAME\":\"规格菜品\",
    // \"TOTALPRICE\":36.00,\
    // "FreePrice\":0.00,
    // \"CHARGEMONEY\":36.00,
    // \"counts\":3.00}

    @SerializedName("PRODUCTNAME")
    String productName;
    @SerializedName("PRODUCTID")
    String productId;
    @SerializedName("ID")
    String id;
    @SerializedName("PRODUCTTYPENAME")
    String productTypeName;
    @SerializedName("TOTALPRICE")
    double totalPrice;
    @SerializedName("FreePrice")
    double freePrice;
    @SerializedName("CHARGEMONEY")
    double chargeMoney;
    @SerializedName("counts")
    double counts;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getFreePrice() {
        return freePrice;
    }

    public void setFreePrice(double freePrice) {
        this.freePrice = freePrice;
    }

    public double getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(double chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public double getCounts() {
        return counts;
    }

    public void setCounts(double counts) {
        this.counts = counts;
    }
}
