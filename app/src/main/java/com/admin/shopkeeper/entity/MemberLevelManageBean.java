package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 *  会员等级管理bean
 * {{\"Guid\":\"f0dbe53e-bc7a-4c2e-8445-3f4f861d2679\",
 * \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
 * \"Name\":\"白金会员\",
 * \"MaxPoints\":3000,
 * \"MinPoints\":200,
 * \"OrderNumber\":\"2017-02-13 17:40:52\"}
 * "Names\":\"智慧餐厅\"
 * }
 */

public class MemberLevelManageBean implements Serializable {
    //会员微信名
    @SerializedName("Guid")
    private String guid;
    //会员名字
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    //会员卡号
    @SerializedName("Name")
    private String name;
    //会员ID
    @SerializedName("MaxPoints")
    private String maxPoints;

    @SerializedName("MinPoints")
    private String minPoints;

    @SerializedName("OrderNumber")
    private String orderNumber;

    @SerializedName("Names")
    private String shopName;

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(String maxPoints) {
        this.maxPoints = maxPoints;
    }

    public String getMinPoints() {
        return minPoints;
    }

    public void setMinPoints(String minPoints) {
        this.minPoints = minPoints;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
