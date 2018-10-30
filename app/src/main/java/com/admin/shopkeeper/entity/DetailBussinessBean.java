package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DetailBussinessBean implements Serializable {
    //PRODUCTNAME 菜品名称
    //TABLENAME 所属桌位
    //OrderNumber 订单编号
    //USERNAME 退菜人
    //COUNT 菜品数量
    //TOTALPRICE 总金额

    @SerializedName("PRODUCTNAME")
    private String productName;
    @SerializedName("TABLENAME")
    private String tableName;
    @SerializedName("OrderNumber")
    private String orderNumber;
    @SerializedName("USERNAME")
    private String username;
    @SerializedName("COUNT")
    private int count;
    @SerializedName("TOTALPRICE")
    private double totlePrice;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotlePrice() {
        return totlePrice;
    }

    public void setTotlePrice(double totlePrice) {
        this.totlePrice = totlePrice;
    }
}
