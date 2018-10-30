package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 商家权限管理名单
 * {
 * "USERNAME":"老板",
 * "PASSWORD":"123",
 * "CREATETIME":"2017-02-12 18:07:10",
 * "USERID":"4399ade8-1d91-4e8d-8143-654e59a8a9e1",
 * "RESTAURANTID":"4b176f0e-0553-4094-8181-5048641b20ef"
 * "ROLEID\":\"5\",\
 * "sex\":\"2\",
 * \"BIRTHDAY\":null,\
 * "DEPARTMENTID\":\"\"
 * }
 */

public class StaffManageBean implements Serializable {
    @SerializedName("USERNAME")
    private String userName;
    @SerializedName("PASSWORD")
    private String password;
    //注册时间
    @SerializedName("CREATETIME")
    private String createTime;
    @SerializedName("RESTAURANTID")
    private String restaurantID;
    @SerializedName("USERID")
    private String userID;
    @SerializedName("ROLEID")
    private int roleID;
    @SerializedName("sex")
    private int sex;
    @SerializedName("BIRTHDAY")
    private String birthday;
    @SerializedName("DEPARTMENTID")
    private String departmentId;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
