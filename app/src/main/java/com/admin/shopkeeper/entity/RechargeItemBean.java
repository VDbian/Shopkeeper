package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/20.
 */

public class RechargeItemBean implements Serializable {
    //{\"GUID\":\"4f62a3ce-25a3-4eb6-95e2-28545436db7d\",
    // \"RESTAURANTID\":\"4B176F0E-0553-4094-8181-5048641B20EF\",
    // \"Type\":1,
    // \"Counts\":10,
    // \"Fill\":100,
    // \"Name\":\"充100送10\",
    // \"Times\":\"2017-10-07 23:40:00\"}

    @SerializedName("GUID")
    private String guid;
    @SerializedName("Type")
    private String type;
    @SerializedName("Counts")
    private String counts;
    @SerializedName("Fill")
    private String fill;
    @SerializedName("Name")
    private String name;
    @SerializedName("Times")
    private String times;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
