package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * {\"DateTimes\":\"2017-10\",\"WeChat\":\"6\",\"Line\":\"4\",\"TOTALPRICE\":\"10\"}
 */

public class MemberVolumeAnalysisBean implements Serializable {

    @SerializedName("DateTimes")
    String date;
    @SerializedName("WeChat")
    String weChat;
    @SerializedName("Line")
    String lineOfDown;
    @SerializedName("TOTALPRICE")
    String total;

    String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getLineOfDown() {
        return lineOfDown;
    }

    public void setLineOfDown(String lineOfDown) {
        this.lineOfDown = lineOfDown;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
