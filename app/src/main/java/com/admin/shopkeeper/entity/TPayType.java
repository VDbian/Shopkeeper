package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/3.
 * {\"pice\":50.00,\"PayType\":\"4\"}
 */

public class TPayType implements Serializable{
    @SerializedName("pice")
    String pice;
    @SerializedName("PayType")
    String payType;

    public String getPice() {
        return pice;
    }

    public String getPayType() {
        return payType;
    }

}
