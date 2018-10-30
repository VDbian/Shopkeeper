package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/7.
 */

public class HouseBean implements Serializable {

    //\"ShangJia\":\"智慧餐厅\",
    // \"NAME\":\"大厅\",
    // \"SORTNO\":\"1\",
    // \"STATE\":1,
    // \"ID\":\"3066a3e0-9a85-4a96-bc11-50a9081b5651\",
    // \"Price\":10.00,
    // \"Counts\":20.00,
    // \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\"},
    @SerializedName("ShangJia")
    private String shangJia;
    @SerializedName("NAME")
    private String name;
    @SerializedName("SORTNO")
    private String sortno;
    @SerializedName("STATE")
    private int state;
    @SerializedName("ID")
    private String id;
    @SerializedName("Price")
    private String price;
    @SerializedName("Counts")
    private String counts;
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    @SerializedName("RoomTypeID")
    private String roomTypeID;

    public String getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(String roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getShangJia() {
        return shangJia;
    }

    public void setShangJia(String shangJia) {
        this.shangJia = shangJia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortno() {
        return sortno;
    }

    public void setSortno(String sortno) {
        this.sortno = sortno;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
