package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 */

public class MemberTranscationBean implements Serializable {
    /**
     * {\"Name\":null,\"ShopName\":\"智慧餐厅\",\"Telephone\":null,
     * \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
     * \"UserID\":\"8467e57f-20af-47e3-aff7-83b4a87ffa8c\",
     * \"1\":\"650.00\",\"RechargeAmount\":\"0\",\"ConsumptionAmount\":\"0\",
     * \"zonadd\":\"0\",\"zonjian\":\"0\",\"yue\":\"0\"}
     */
    @SerializedName("Name")
    String name;
    @SerializedName("ShopName")
    String shopName;
    @SerializedName("Telephone")
    String phone;
    @SerializedName("RESTAURANTID")
    String shopID;
    @SerializedName("UserID")
    String userID;
    @SerializedName("RechargeAmount")
    String rechargeMoney;
    @SerializedName("ConsumptionAmount")
    String usedMoney;
    @SerializedName("zonadd")
    String zonAdd;
    @SerializedName("zonjian")
    String zonJian;
    @SerializedName("yue")
    String yue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(String rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getUsedMoney() {
        return usedMoney;
    }

    public void setUsedMoney(String usedMoney) {
        this.usedMoney = usedMoney;
    }

    public String getZonAdd() {
        return zonAdd;
    }

    public void setZonAdd(String zonAdd) {
        this.zonAdd = zonAdd;
    }

    public String getZonJian() {
        return zonJian;
    }

    public void setZonJian(String zonJian) {
        this.zonJian = zonJian;
    }

    public String getYue() {
        return yue;
    }

    public void setYue(String yue) {
        this.yue = yue;
    }
}
