package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by guxiaogasumi on 2017/6/17.
 * "ID":"3066a3e0-9a85-4a96-bc11-50a9081b5651",
 * "RESTAURANTID":"4b176f0e-0553-4094-8181-5048641b20ef",
 * "NAME":"大厅",
 * "SORTNO":"1",
 * "STATE":1,
 * "RoomTypeID":"63257b4e-7fa1-4d1a-a9b7-f663f5d000c4",
 * "AreaId":null,
 * "Price":10,
 * "Counts":20
 */
@Entity
public class RoomEntity {
    @Id
    @SerializedName("ID")
    private String id;
    @SerializedName("NAME")
    private String name;
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    @Transient
    @SerializedName("SORTNO")
    private String sortno;
    @Transient
    @SerializedName("STATE")
    private int state;
    @Transient
    @SerializedName("RoomTypeID")
    private String roomTypeID;
    @Transient
    @SerializedName("AreaId")
    private String areaId;
    @Transient
    @SerializedName("Price")
    private double price;
    @Transient
    @SerializedName("Counts")
    private String counts;

    @Generated(hash = 2079473466)
    public RoomEntity(String id, String name, String restaurantId) {
        this.id = id;
        this.name = name;
        this.restaurantId = restaurantId;
    }

    @Generated(hash = 1023035664)
    public RoomEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
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

    public String getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(String roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }
}
