package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DeskTypeBean implements Serializable {

    //{\"Guid\":\"f14452ea-872c-4232-b0b4-039e46e1223d\",
    // \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
    // \"Name\":\"小桌\",
    // \"PersonCount\":\"da48a4fe-4563-411c-aa21-fcb7041d22a7\",
    // \"PersonCountInfo\":\"2-3人\",
    // \"Types\":\"B\"}
    @SerializedName("Guid")
    String guid;
    @SerializedName("RESTAURANTID")
    String restaurantId;
    @SerializedName("Name")
    String name;
    @SerializedName("PersonCount")
    String personCount;
    @SerializedName("PersonCountInfo")
    String personCountInfo;
    @SerializedName("Types")
    String types;

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

    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
    }

    public String getPersonCountInfo() {
        return personCountInfo;
    }

    public void setPersonCountInfo(String personCountInfo) {
        this.personCountInfo = personCountInfo;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
