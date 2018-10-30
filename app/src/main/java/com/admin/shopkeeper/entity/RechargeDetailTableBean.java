package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 * {\"DateTimes\":\"2017-10\",\"weixin\":\"500.00\",\"zengson\":\"150.00\",
 * \"diannei\":\"100.00\",\"TOTALPRICE\":\"750.00\"}
 *
 * {\"DateTimes\":\"2018-03\",\"zengson\":\"1038.00\",\"diannei\":\"132644.00\",\"TOTALPRICE\":\"133682.00\",\"weixin\":\"0\"}
 */

public class RechargeDetailTableBean implements Serializable {

    @SerializedName("DateTimes")
    String date;
    @SerializedName("weixin")
    double weixin;
    @SerializedName("zengson")
    double zengsong;
    @SerializedName("diannei")
    double diannei;
    @SerializedName("TOTALPRICE")
    double totalPrice;

    String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWeixin() {
        return weixin;
    }

    public void setWeixin(double weixin) {
        this.weixin = weixin;
    }

    public double getZengsong() {
        return zengsong;
    }

    public void setZengsong(double zengsong) {
        this.zengsong = zengsong;
    }

    public double getDiannei() {
        return diannei;
    }

    public void setDiannei(double diannei) {
        this.diannei = diannei;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
