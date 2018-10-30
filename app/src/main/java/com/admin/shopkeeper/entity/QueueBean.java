package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/20.
 */

public class QueueBean implements Serializable {

//    GUIDS  排号ID
//    RESTAURANTID 店铺名称
//    Username 排号人姓名
//    Phone  排号人电话
//    DINNERDATE  日期
//    DINNERTIME  日期时间
//    Tablename  桌位类别
//    CALLNO  排号

    //{\"STATE\":0,
    // \"GUIDS\":\"02b775ef-51b0-49fa-abe7-c60c4ec5db4a\",
    // \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
    // \"username\":\"赵飞\",
    // \"Phone\":\"13402806964\",
    // \"DINNERDATE\":\"2017-09-20 00:00:00\",
    // \"DINNERTIME\":\"2017-09-20 11:11:53\",
    // \"tablename\":\"大桌\",
    // \"CALLNO\":1,
    // \"Types\":\"A\"}

    @SerializedName("STATE")
    private int state;
    @SerializedName("GUIDS")
    private String guid;
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    @SerializedName("username")
    private String username;
    @SerializedName("Phone")
    private String phone;
    @SerializedName("DINNERDATE")
    private String dinnerDate;
    @SerializedName("DINNERTIME")
    private String dinnerTime;
    @SerializedName("tablename")
    private String tableName;
    @SerializedName("CALLNO")
    private int callNo;
    @SerializedName("Types")
    private String types;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDinnerDate() {
        return dinnerDate;
    }

    public void setDinnerDate(String dinnerDate) {
        this.dinnerDate = dinnerDate;
    }

    public String getDinnerTime() {
        return dinnerTime;
    }

    public void setDinnerTime(String dinnerTime) {
        this.dinnerTime = dinnerTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNo() {
        return types + callNo;
    }

    public int getCallNo() {
        return callNo;
    }

    public void setCallNo(int callNo) {
        this.callNo = callNo;
    }

}
