package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/11.
 */

public class BussinessBean implements Serializable {
    //{\"PERSONCOUNT\":null,\"CHARGEMONEY\":null,\"FREEMONEY\":null,\"Count\":null,\"TOTALMONEY\":\"0\",\"Tablecount\":\"0\",\"Fancount\":\"0\",\"chetablecount\":\"0\"}
    @SerializedName("PERSONCOUNT")
    private int personCount;
    @SerializedName("CHARGEMONEY")
    private double chargeMoney;
    @SerializedName("FREEMONEY")
    private double freeMoney;
    @SerializedName("Count")
    private double count;
    @SerializedName("TOTALMONEY")
    private double totalMoney;
    @SerializedName("Tablecount")
    private double tableCount;
    @SerializedName("Fancount")
    private int fanCount;
    @SerializedName("chetablecount")
    private int cheTableCount;

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getTableCount() {
        return tableCount;
    }

    public void setTableCount(double tableCount) {
        this.tableCount = tableCount;
    }

    public int getFanCount() {
        return fanCount;
    }

    public void setFanCount(int fanCount) {
        this.fanCount = fanCount;
    }

    public int getCheTableCount() {
        return cheTableCount;
    }

    public void setCheTableCount(int cheTableCount) {
        this.cheTableCount = cheTableCount;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public double getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(double chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public double getFreeMoney() {
        return freeMoney;
    }

    public void setFreeMoney(double freeMoney) {
        this.freeMoney = freeMoney;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
