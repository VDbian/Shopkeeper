package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 */

public class MemberConsumeDetailBean implements Serializable {
    /**
     {\"UserID\":\"947230e5-a8dc-4c22-8e31-215b33a375c8\",
     \"StaffDepart\":\"王月\",\"StaffTel\":\"17778063182\",
     \"xiao\":\"2017-10-27 14:30:19\",\"counts\":1,\"prices\":70.00}
     */
    @SerializedName("UserID")
    String userID;
    @SerializedName("StaffDepart")
    String userName;
    @SerializedName("StaffTel")
    String phone;
    @SerializedName("xiao")
    String date;
    @SerializedName("counts")
    String counts;
    @SerializedName("prices")
    String price;

    String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
