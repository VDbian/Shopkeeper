package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/27.
 */

public class ReturnStatisticsBean implements Serializable {
    //PRODUCTNAME 菜品名称
    //COUNTS 退菜数量
    //PRICE退菜金额
    //Pcounts 涉及单数
    @SerializedName("PRODUCTNAME")
    String productName;
    @SerializedName("COUNTS")
    int counts;
    @SerializedName("PRICE")
    double price;
    @SerializedName("pcounts")
    int pcounts;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPcounts() {
        return pcounts;
    }

    public void setPcounts(int pcounts) {
        this.pcounts = pcounts;
    }
}
