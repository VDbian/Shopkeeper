package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DeskBean implements Serializable {

    ///{\"fangjian\":\"31-60号\",
    /// \"ROOMTABLEID\":\"03e0b176-d457-4949-a7a6-0bab997d61da\",
    /// \"TABLENAME\":\"56号\",
    /// \"PERSONCOUNT\":\"1\",
    /// \"SORTNO\":56,
    /// \"RESTAURANTID\":\"55e1db2c-a53e-45fa-b8a8-66e76c188429\"},
    @SerializedName("fangjian")
    private String fangjian;
    @SerializedName("ROOMTABLEID")
    private String roomtableId;
    @SerializedName("TABLENAME")
    private String tableName;
    @SerializedName("PERSONCOUNT")
    private String personCount;
    @SerializedName("SORTNO")
    private int sortNo;
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    @SerializedName("WeiXinType")
    private String weiXinType;

    public String getWeiXinType() {
        return weiXinType;
    }

    public void setWeiXinType(String weiXinType) {
        this.weiXinType = weiXinType;
    }

    public String getFangjian() {
        return fangjian;
    }

    public void setFangjian(String fangjian) {
        this.fangjian = fangjian;
    }

    public String getRoomtableId() {
        return roomtableId;
    }

    public void setRoomtableId(String roomtableId) {
        this.roomtableId = roomtableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
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
