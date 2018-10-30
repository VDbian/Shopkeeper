package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/20.
 */

public class RechargeBean implements Serializable {
//{\"ID\":\"0107b891-c1c2-4f46-8eb3-9be88265e8da\",
// \"StaffDepart\":\"哈哈\",
// \"StaffCard\":\"171020125026613135\",
// \"StaffName\":null,
// \"StaffTel\":\"13890330888\",
// \"StaffEmail\":null,
// \"StaffAddTime\":\"2017-10-20 12:50:26\",
// \"RESTAURANTID\":\"4B176F0E-0553-4094-8181-5048641B20EF\"}

    @SerializedName("ID")
    private String id;
    @SerializedName("StaffDepart")
    private String staffDepart;
    @SerializedName("StaffCard")
    private String staffCard;
    @SerializedName("StaffName")
    private String staffName;
    @SerializedName("StaffTel")
    private String staffTel;
    @SerializedName("StaffEmail")
    private String staffEmail;
    @SerializedName("StaffAddTime")
    private String staffAddTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffDepart() {
        return staffDepart;
    }

    public void setStaffDepart(String staffDepart) {
        this.staffDepart = staffDepart;
    }

    public String getStaffCard() {
        return staffCard;
    }

    public void setStaffCard(String staffCard) {
        this.staffCard = staffCard;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffTel() {
        return staffTel;
    }

    public void setStaffTel(String staffTel) {
        this.staffTel = staffTel;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getStaffAddTime() {
        return staffAddTime;
    }

    public void setStaffAddTime(String staffAddTime) {
        this.staffAddTime = staffAddTime;
    }
}
