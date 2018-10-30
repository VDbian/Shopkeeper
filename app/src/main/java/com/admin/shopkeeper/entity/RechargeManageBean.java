package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 会员等级管理bean
 * {\"GUID\":\"9f8254a4-2424-4575-a8d9-2c25ebf68626\",
 * \"RESTAURANTID\":\"4B176F0E-0553-4094-8181-5048641B20EF\",
 * \"Type\":1,
 * \"Counts\":100,
 * \"Fill\":1000,
 * \"Name\":\"啦啦啦\",
 * \"Times\":\"2017-09-15 16:27:43\"}
 * }
 */

public class RechargeManageBean implements Serializable {
    //id
    @SerializedName("GUID")
    private String guid;
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    //赠送金额
    @SerializedName("Counts")
    private String giveMoney;
    //充值金额
    @SerializedName("Fill")
    private String rechargeMoney;

    @SerializedName("Times")
    private String time;
    //赠送方式
    @SerializedName("Type")
    private String type;

    @SerializedName("Name")
    private String name;

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getGiveMoney() {
        return giveMoney;
    }

    public void setGiveMoney(String giveMoney) {
        this.giveMoney = giveMoney;
    }

    public String getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(String rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
