package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by admin on 2017/3/28.
 * {
 * "USERID":"4399ade8-1d91-4e8d-8143-654e59a8a9e1",
 * "RESTAURANTID":"4b176f0e-0553-4094-8181-5048641b20ef",
 * "USERNAME":"老板",
 * "PASSWORD":"123",
 * "ROLEID":"2",
 * "CREATETIME":"2017-02-12 18:07:10",
 * "STATE":1
 * \"CashPayType\":\"1,2\"
 * "PayType\":\"3\"
 * "MasterType\":\"1\"
 * }
 */

@Entity
public class User implements Serializable {
    static final long serialVersionUID = 42L;
    @Id
    @SerializedName("USERID")
    private String id;

    @SerializedName("USERNAME")
    private String name;

    @SerializedName("ROLEID")
    private String roleID;

    @SerializedName("RESTAURANTID")
    private String restaurantID;

    @SerializedName("STATE")
    private int state;

    @SerializedName("OperaType")
    private String operaType;

    @SerializedName("PermissionName")
    private String permissionName;

    @SerializedName("PermissionValue")
    private String permissionValue;

    @SerializedName("PrintSet")
    private String printSet;

    @SerializedName("PayType")
    private String payType;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @SerializedName("CashPayType")

    private String cashPayType;

    @SerializedName("MasterType")
    private String masterType;

    public String getMasterType() {
        return masterType;
    }

    public void setMasterType(String masterType) {
        this.masterType = masterType;
    }

    public String getCashPayType() {
        return cashPayType;
    }

    public void setCashPayType(String cashPayType) {
        this.cashPayType = cashPayType;
    }

    public String getPrintSet() {
        return printSet;
    }

    public void setPrintSet(String printSet) {
        this.printSet = printSet;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public String getOperaType() {
        return operaType;
    }

    public void setOperaType(String operaType) {
        this.operaType = operaType;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    @Generated(hash = 2035955160)
    public User(String id, String name, String roleID, String restaurantID,
            int state, String operaType, String permissionName,
            String permissionValue, String printSet, String payType,
            String cashPayType, String masterType) {
        this.id = id;
        this.name = name;
        this.roleID = roleID;
        this.restaurantID = restaurantID;
        this.state = state;
        this.operaType = operaType;
        this.permissionName = permissionName;
        this.permissionValue = permissionValue;
        this.printSet = printSet;
        this.payType = payType;
        this.cashPayType = cashPayType;
        this.masterType = masterType;
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

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
