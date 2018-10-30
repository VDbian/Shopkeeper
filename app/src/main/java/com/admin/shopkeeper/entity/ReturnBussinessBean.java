package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ReturnBussinessBean implements Serializable {

    //TABLENAME 菜品名称
    //Counts 菜品数量
    //Price 总金额

    @SerializedName("TABLENAME")
    private String tablename;
    @SerializedName("counts")
    private int counts;
    @SerializedName("price")
    private double price;
    @SerializedName("ROOMTABLEID")
    private String roomTableId;

    public String getRoomTableId() {
        return roomTableId;
    }

    public void setRoomTableId(String roomTableId) {
        this.roomTableId = roomTableId;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
