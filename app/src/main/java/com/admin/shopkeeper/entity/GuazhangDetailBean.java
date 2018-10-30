package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/11.
 */

public class GuazhangDetailBean implements Serializable {
    //{\"BILLID\":\"476c4b57-4e5d-4565-a7f4-46c4bd3aaad8\",
//\"TABLENAME\":\"8号\",\"INPUTTIME\":\"2017-09-11 16:39:33\",\"CHARGEMONEY\":136.00,\"USERIDGUATYPE\":\"0\"}
    @SerializedName("BILLID")
    private String billId;
    @SerializedName("TABLENAME")
    private String tableName;
    @SerializedName("INPUTTIME")
    private String inputTime;
    @SerializedName("CHARGEMONEY")
    private double chargeMoney;
    @SerializedName("USERIDGUATYPE")
    private String userIdGuaType;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getInputTime() {
        return inputTime;
    }

    public void setInputTime(String inputTime) {
        this.inputTime = inputTime;
    }

    public double getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(double chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public String getUserIdGuaType() {
        return userIdGuaType;
    }

    public void setUserIdGuaType(String userIdGuaType) {
        this.userIdGuaType = userIdGuaType;
    }
}
