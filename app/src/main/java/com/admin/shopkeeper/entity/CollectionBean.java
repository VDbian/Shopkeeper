package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 */

public class CollectionBean implements Serializable {

    @SerializedName("Pice")
    String pice;
    @SerializedName("PayType")
    String payType;
    @SerializedName("count")
    String count;

    public String getPice() {
        return pice;
    }

    public void setPice(String pice) {
        this.pice = pice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
