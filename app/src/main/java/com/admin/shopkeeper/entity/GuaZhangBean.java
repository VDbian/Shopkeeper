package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/30.
 */

public class GuaZhangBean implements Serializable{

    //{\"Name\":\"曹波\",\"GUID\":\"ec2b5250-690d-4fa7-8988-e66355e3f3f1\",\"RESTAURANTID\":\"4B176F0E-0553-4094-8181-5048641B20EF\"}
    //{\"GUID\":\"e7a7c829-c592-4863-8a72-046d956de1ce\",\"RESTAURANTID\":\"4B176F0E-0553-4094-8181-5048641B20EF\",\"Name\":\"李明\",
    // \"Phone\":\"18011440711\",\"Ramark\":\"\"}
    @SerializedName("GUID")
    String guid;
    @SerializedName("Name")
    String name;
    @SerializedName("RESTAURANTID")
    String restaurantId;
    @SerializedName("Phone")
    private String phone;
    @SerializedName("Ramark")
    private String remark;

    boolean isSelected;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
