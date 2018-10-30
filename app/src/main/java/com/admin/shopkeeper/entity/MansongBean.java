package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2017/10/31.
 */

public class MansongBean implements Serializable {
    //{\"Guid\":\"02c6d85f-8952-44b6-9f89-71d23dcd5795\",
    // \"Name\":\"满100送50元\",
    // \"TiaoJian\":100,
    // \"JinEr\":50,
    // \"IsBegin\":\"1\",
    // \"Btime\":\"2017-07-19 00:00:00\",
    // \"Etime\":\"2017-07-31 23:59:59\",
    // \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
    // \"Time\":\"2017-02-15 16:19:54\",
    // \"Apply\":\"5\"}
    @SerializedName("Guid")
    String guid;
    @SerializedName("Name")
    String name;
    @SerializedName("TiaoJian")
    int tiaojian;
    @SerializedName("JinEr")
    int money;
    @SerializedName("IsBegin")
    String isBegin;
    @SerializedName("Btime")
    String bTime;
    @SerializedName("Etime")
    String eTime;
    @SerializedName("RESTAURANTID")
    String shopId;
    @SerializedName("Time")
    String time;
    @SerializedName("Apply")
    String apply;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTiaojian() {
        return tiaojian;
    }

    public void setTiaojian(int tiaojian) {
        this.tiaojian = tiaojian;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getIsBegin() {
        return isBegin;
    }

    public void setIsBegin(String isBegin) {
        this.isBegin = isBegin;
    }

    public String getbTime() {
        return bTime;
    }

    public void setbTime(String bTime) {
        this.bTime = bTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }
}
