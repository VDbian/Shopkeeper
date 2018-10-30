package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 {\"STATE\":2,\"BILLID\":\"08a9bfb2-ab7e-46d7-b8da-d4c974043411\",
 \"ID\":\"0870552f-07bb-4213-b73b-485666a3cf97\",
 \"times\":\"2017-10-31 16:28:04\",\"OrderNumber\":\"1710311500017232\",
 \"uname\":\"收银\",\"pice\":72.00,\"TABLENAME\":\"6号\"}
 */

public class SensitiveOpearation implements Serializable {
    @SerializedName("MingGan")
    private String sensitiveState;

    public String getSensitiveState() {
        return sensitiveState;
    }

    public void setSensitiveState(String sensitiveState) {
        this.sensitiveState = sensitiveState;
    }

    @SerializedName("STATE")
    private String state;
    @SerializedName("BILLID")
    private String billId;
    @SerializedName("ID")
    private String id;
    @SerializedName("times")
    private String times;
    @SerializedName("OrderNumber")
    private String orderNumber;
    @SerializedName("uname")
    private String userName;
    @SerializedName("pice")
    private double pice;
    @SerializedName("TABLENAME")
    private String tableNmae;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getPice() {
        return pice;
    }

    public void setPice(double pice) {
        this.pice = pice;
    }

    public String getTableNmae() {
        return tableNmae;
    }

    public void setTableNmae(String tableNmae) {
        this.tableNmae = tableNmae;
    }
}
