package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 */

public class CouponDetailTableBean implements Serializable {
    /**
     {\"Name\":\"10元代金券\",\"huocount\":\"0\",
     \"count\":\"100\",\"usecount\":\"0\",\"freeprice\":\"0\",
     \"uselv\":\"0%\",\"xiaofei\":\"0\",\"yfreeprice\":\"10.00\"}
     */
    @SerializedName("Name")
    String couponName;
    @SerializedName("huocount")
    String couponPerson;
    @SerializedName("count")
    String giveConponVolume;
    @SerializedName("usecount")
    String useCoupon;
    @SerializedName("freeprice")
    String freePriceByCoupon;
    @SerializedName("uselv")
    String useLv;
    @SerializedName("xiaofei")
    String consumeByCoupon;
    @SerializedName("yfreeprice")
    String discount;

    private String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponPerson() {
        return couponPerson;
    }

    public void setCouponPerson(String couponPerson) {
        this.couponPerson = couponPerson;
    }

    public String getGiveConponVolume() {
        return giveConponVolume;
    }

    public void setGiveConponVolume(String giveConponVolume) {
        this.giveConponVolume = giveConponVolume;
    }

    public String getUseCoupon() {
        return useCoupon;
    }

    public void setUseCoupon(String useCoupon) {
        this.useCoupon = useCoupon;
    }

    public String getFreePriceByCoupon() {
        return freePriceByCoupon;
    }

    public void setFreePriceByCoupon(String freePriceByCoupon) {
        this.freePriceByCoupon = freePriceByCoupon;
    }

    public String getUseLv() {
        return useLv;
    }

    public void setUseLv(String useLv) {
        this.useLv = useLv;
    }

    public String getConsumeByCoupon() {
        return consumeByCoupon;
    }

    public void setConsumeByCoupon(String consumeByCoupon) {
        this.consumeByCoupon = consumeByCoupon;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
