package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 单个会员记录
 * {\"PayType\":1,
 * \"Category\":\"2\",
 * \"Price\":500.00,\
 * "Times\":\"2016-01-17 01:28:36\"}
 * Type 获取方式
 * Counts 积分减少
 * IntergialTypeID 卡卷类别
 * EnterTime 获取时间
 * (UseTime 使用时间
 * Times 操作时间
 */

public class MemberInfoOfItemBean implements Serializable {
    //支付类别
    @SerializedName("PayType")
    private String payType;
    //消费类别
    @SerializedName("Category")
    private String category;
    //消费金额
    @SerializedName("Price")
    private double price;
    //消费时间
    @SerializedName("DateTimes")
    private String times;
    @SerializedName("Type")
    private String type;
    @SerializedName("IntergialTypeID")
    private String intergialTypeID;
    @SerializedName("EnterTime")
    private String enterTime;
    @SerializedName("UseTime")
    private String userTime;
    @SerializedName("Counts")
    private String counts;

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntergialTypeID() {
        return intergialTypeID;
    }

    public void setIntergialTypeID(String intergialTypeID) {
        this.intergialTypeID = intergialTypeID;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
