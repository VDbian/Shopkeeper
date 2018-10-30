package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/27.
 */

public class FreeDetailBean implements Serializable {
    //{\"PayType\":\"优惠(代金)券\",\"Pice\":\"0\",\"count\":\"0\"}
    @SerializedName("PayType")
    String payType;
    @SerializedName("Pice")
    String pice;
    @SerializedName("count")
    String count;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPice() {
        return pice;
    }

    public void setPice(String pice) {
        this.pice = pice;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
