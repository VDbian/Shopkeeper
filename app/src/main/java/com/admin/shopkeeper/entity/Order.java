package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zeng on 2017/4/17.
 * {
 * "RESTAURANTID":"4B176F0E-0553-4094-8181-5048641B20EF",
 * "ID":"cfc5906a-c7c6-48a7-8d3e-adac6fc8cec7",
 * "OrderNumber":"170703235841544176",
 * "Type":"1",
 * "OrderSate":"1",
 * "OrderSource":"1",
 * "BILLID":"fc57e205-bec8-427c-8739-e2f428bc8a28",
 * "PayType":"3",
 * "PayPrice":null,
 * "ActuelPayPrice":0,
 * "IsPay":"1",
 * "RecordDate":"2017-07-03 23:58:41",
 * "Remark":"0"
 * }
 */

public class Order implements Serializable {
    @SerializedName("ID")
    private String id;
    @SerializedName("RESTAURANTID")
    private String restaurantID;
    @SerializedName("OrderNumber")
    private String orderNumber;
    @SerializedName("Type")
    private String type;
    @SerializedName("BILLID")
    private String billid;
    @SerializedName("PayType")
    private String payType;
    @SerializedName("ActuelPayPrice")
    private double actuelPayPrice;
    @SerializedName("IsPay")
    private String payIs;
    @SerializedName("RecordDate")
    private String recordDate;
    @SerializedName("Remark")
    private String remark;
    @SerializedName("PayPrice")
    private double payPrice;
    @SerializedName("OrderSate")
    private String orderSate;
    @SerializedName("OrderSource")
    private String orderSource;
    @SerializedName("USERNAME")
    private String username;
    @SerializedName("TABLENAME")
    private String tableName;
    @SerializedName("TABLEID")
    private String tableId;
    @SerializedName("PERSONCOUNT")
    private int peopleCount;
    @SerializedName("TableWareCount")
    private int tableWareCount;
    @SerializedName("STATE")
    private int state;
    @SerializedName("BILLIDMERGER")
    private String billIdMerger;

    public String getBillIdMerger() {
        return billIdMerger;
    }

    public void setBillIdMerger(String billIdMerger) {
        this.billIdMerger = billIdMerger;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public int getTableWareCount() {
        return tableWareCount;
    }

    public void setTableWareCount(int tableWareCount) {
        this.tableWareCount = tableWareCount;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrderSate() {
        return orderSate;
    }

    public void setOrderSate(String orderSate) {
        this.orderSate = orderSate;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getActuelPayPrice() {
        return actuelPayPrice;
    }

    public void setActuelPayPrice(double actuelPayPrice) {
        this.actuelPayPrice = actuelPayPrice;
    }

    public String getPayIs() {
        return payIs;
    }

    public void setPayIs(String payIs) {
        this.payIs = payIs;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", restaurantID='" + restaurantID + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", type='" + type + '\'' +
                ", billid='" + billid + '\'' +
                ", payType='" + payType + '\'' +
                ", actuelPayPrice=" + actuelPayPrice +
                ", payIs='" + payIs + '\'' +
                ", recordDate='" + recordDate + '\'' +
                ", remark='" + remark + '\'' +
                ", payPrice=" + payPrice +
                ", orderSate='" + orderSate + '\'' +
                ", orderSource='" + orderSource + '\'' +
                ", username='" + username + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableId='" + tableId + '\'' +
                ", peopleCount=" + peopleCount +
                ", tableWareCount=" + tableWareCount +
                ", state=" + state +
                '}';
    }
}
