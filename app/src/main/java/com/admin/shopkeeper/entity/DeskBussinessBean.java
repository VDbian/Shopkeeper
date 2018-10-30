package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DeskBussinessBean implements Serializable {

    //{\"TABLENAME\":\"6号\",
    // \"counts\":null,
    // \"person\":null,
    // \"price\":null,
    // \"ROOMTABLEID\":\"030531c5-2aec-4a73-aaaa-206213b27c6d\",
    // \"SORTNO\":6,
    // \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\"}
    @SerializedName("TABLENAME")
    String tableName;
    @SerializedName("counts")
    int counts;
    @SerializedName("person")
    int persion;
    @SerializedName("price")
    double price;
    @SerializedName("ROOMTABLEID")
    String roomTableId;
    @SerializedName("SORTNO")
    int sortNo;
    @SerializedName("RESTAURANTID")
    String restaurantId;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getPersion() {
        return persion;
    }

    public void setPersion(int persion) {
        this.persion = persion;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRoomTableId() {
        return roomTableId;
    }

    public void setRoomTableId(String roomTableId) {
        this.roomTableId = roomTableId;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
