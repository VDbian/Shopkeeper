package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * {
 * {\"BILLID\":\"f3580e3d-8bb6-4d80-88ba-70c19d5a480e\",
 * \"RESTAURANTID\":\"4B176F0E-0553-4094-8181-5048641B20EF\"
 * ,\"PayType\":\"1\",
 * \"TABLENAME\":\"5号\",
 * \"PERSONCOUNT\":1,
 * \"DINNERTIME\":\"2017-10-31 10:41:28\",
 * \"TOTALMONEY\":58.00,
 * \"FREEMONEY\":0.00,
 * \"CHARGEMONEY\":58.00,
 * \"STATE\":3,\"REAMRK\":null,
 * \"Type\":\"7\",\"OrderNumber\":\"17103110410860494\",
 * \"OrderSate\":\"3\",\"USERNAME\":\"收银\"}
 * }
 */

public class OrderManage implements Serializable {
    @SerializedName("BILLID")
    private String id;
    @SerializedName("RESTAURANTID")
    private String restaurantID;
    @SerializedName("PayType")
    private String payType;
    @SerializedName("TABLENAME")
    private String tableName;
    @SerializedName("PERSONCOUNT")
    private int peopleCount;
    @SerializedName("DINNERTIME")
    private String jieZhangTime;
    @SerializedName("TOTALMONEY")
    private double totalMoney;
    @SerializedName("FREEMONEY")
    private double freeMoney;
    @SerializedName("CHARGEMONEY")
    private double chargeMoney;
    @SerializedName("STATE")
    private int state;
    @SerializedName("REAMRK")
    private String remark;
    @SerializedName("Type")//订单类型
    private String type;
    @SerializedName("OrderNumber")//订单编号
    private String orderNumber;
    @SerializedName("OrderSate")
    private String orderSate;
    @SerializedName("USERNAME")
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getJieZhangTime() {
        return jieZhangTime;
    }

    public void setJieZhangTime(String jieZhangTime) {
        this.jieZhangTime = jieZhangTime;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderSate() {
        return orderSate;
    }

    public void setOrderSate(String orderSate) {
        this.orderSate = orderSate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
