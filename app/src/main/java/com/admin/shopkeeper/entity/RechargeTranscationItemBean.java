package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 * {\"PayType\":4,\"PiCi\":\"店内支付\",\"Category\":\"店内支付\",\"Price\":500.00,\"Times\":\"2017-10-28 13:44:34\"}
 */

public class RechargeTranscationItemBean implements Serializable {

    @SerializedName("Category")
    String consumeType;
    @SerializedName("PiCi")
    String payType;
    @SerializedName("Price")
    String price;
    @SerializedName("Times")
    String time;

    public String getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(String consumeType) {
        this.consumeType = consumeType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
