package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 */

public class IntegralDetailTableBean implements Serializable {
    /**
     {\"Name\":\"120012456789\",\"ShopName\":\"智慧餐厅\",
     \"Telephone\":\"120012456789\",\"RESTAURANTID\":\"4B176F0E-0553-4094-8181-5048641B20EF\",
     \"UserID\":\"1b576e3d-e309-4323-9aa6-2c109750640c\",\"RechargeAmount\":\"100\",
     \"ConsumptionAmount\":null,\"zonadd\":\"100\",\"zonjian\":\"0\",\"yue\":\"100\"}
     */
    @SerializedName("Name")
    String userName;
    @SerializedName("ShopName")
    String shopName;
    @SerializedName("Telephone")
    String phone;
    @SerializedName("RESTAURANTID")
    String shopID;
    @SerializedName("UserID")
    String userID;
    @SerializedName("RechargeAmount")
    String rechargeAmount;
    @SerializedName("ConsumptionAmount")
    String consumeAmount;
    @SerializedName("zonadd")
    String add;
    @SerializedName("zonjian")
    String reduce;
    @SerializedName("yue")
    String yue;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(String consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getReduce() {
        return reduce;
    }

    public void setReduce(String reduce) {
        this.reduce = reduce;
    }

    public String getYue() {
        return yue;
    }

    public void setYue(String yue) {
        this.yue = yue;
    }
}
