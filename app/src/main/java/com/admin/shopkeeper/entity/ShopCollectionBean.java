package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 */

public class ShopCollectionBean implements Serializable {
    //{\"DINNERDATE\":\"2017-10-25 \",\"TOTALMONEY\":3844.00,\"FREEMONEY\":1.00,\"CHARGEMONEY\":3843.00,\"ChonZhi\":6.00}
    @SerializedName("DINNERDATE")
    String dinnerDate;
    @SerializedName("TOTALMONEY")
    double totalMoney;
    @SerializedName("FREEMONEY")
    double freeMoney;
    @SerializedName("CHARGEMONEY")
    double chargeMoney;
    @SerializedName("ChonZhi")
    double chongzhi;
    @SerializedName("Names")
    String names;
    @SerializedName("RESTAURANTID")
    String shopId;

    String startTime;
    String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getDinnerDate() {
        return dinnerDate;
    }

    public void setDinnerDate(String dinnerDate) {
        this.dinnerDate = dinnerDate;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getFreeMoney() {
        return freeMoney;
    }

    public void setFreeMoney(double freeMoney) {
        this.freeMoney = freeMoney;
    }

    public double getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(double chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public double getChongzhi() {
        return chongzhi;
    }

    public void setChongzhi(double chongzhi) {
        this.chongzhi = chongzhi;
    }
}
