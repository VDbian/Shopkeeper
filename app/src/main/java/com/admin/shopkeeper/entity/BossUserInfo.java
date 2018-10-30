package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;


import java.io.Serializable;

/**
 * 老板端用户信息
 * {
 * GUID":"4b176f0e-0553-4094-8181-5048641b20ef",
 * "UserName":"15982187353",
 * "PassWord":"123456","
 * Status":1,"
 * Phone":"15982187353","
 * Names":"新创智慧餐厅",
 * "Address":"天府软件园",
 * "Type":1,
 * "RegTime":"2017-02-12 18:01:32",
 * "LastLoginTime":null,
 * "ImageCategoryId":null,
 * "PassTime":"2017-02-12 18:01:53","
 * List":null,"
 * ReportdoctorGUID":"81299f76-6912-4812-b52c-8b751b17064d",
 * "AccountType":1,
 * "PackageValue":"50",
 * "PacsIP":null,
 * "Btime":"00:00:00",
 * "Etime":"23:59:00",
 * "CarCount":8,
 * "Longitude":"104.085927",
 * "Latitude":"30.541735",
 * "OperaType":"1,2",
 * "Lable":"1,2,3,4,5",
 * "StaffID":null,
 * "DeveName":""
 * }
 */

public class BossUserInfo implements Serializable {
    @SerializedName("GUID")
    private String guId;
    @SerializedName("UserName")
    private String name;
    @SerializedName("PassWord")
    private String password;

    @SerializedName("RESTAURANTID")
    private String restaurantID;

    @SerializedName("Status")
    private int status;

    @SerializedName("Phone")
    private String phone;
    //餐厅名字
    @SerializedName("Names")
    private String shopNames;

    @SerializedName("Address")
    private String address;

    @SerializedName("Type")
    private int type;

    @SerializedName("RegTime")
    private String regTime;
    @SerializedName("LastLoginTime")
    private String lastLoginTime;
    @SerializedName("ImageCategoryId")
    private String imageCategoryId;
    /**
     * 审核通过时间
     */
    @SerializedName("PassTime")
    private String passTime;
    @SerializedName("List")
    private String list;
    @SerializedName("ReportdoctorGUID")
    private String reportDoctorGUID;
    @SerializedName("AccountType")
    private String accountType;
    //平均值
    @SerializedName("PackageValue")
    private String packageValue;
    @SerializedName("PacsIP")
    private String pacsIp;
    @SerializedName("Btime")
    private String bTime;
    @SerializedName("Etime")
    private String eTime;
    //车位
    @SerializedName("CarCount")
    private String carCount;
    @SerializedName("Longitude")
    private String longitude;
    @SerializedName("Latitude")
    private String latitude;
    @SerializedName("OperaType")
    private String operaType;
    @SerializedName("Lable")
    private String lable;
    @SerializedName("StaffID")
    private String staffId;
    @SerializedName("DeveName")
    private String deveName;

    public BossUserInfo() {
    }
    public BossUserInfo(String guId, String name, String password, String restaurantID,
                        int status, String phone, String shopNames, String address, int type,
                        String regTime, String lastLoginTime, String imageCategoryId, String passTime,
                        String list, String reportDoctorGUID, String accountType, String packageValue,
                        String pacsIp, String bTime, String eTime, String carCount, String longitude,
                        String latitude, String operaType, String lable, String staffId, String deveName) {
        this.guId = guId;
        this.name = name;
        this.password = password;
        this.restaurantID = restaurantID;
        this.status = status;
        this.phone = phone;
        this.shopNames = shopNames;
        this.address = address;
        this.type = type;
        this.regTime = regTime;
        this.lastLoginTime = lastLoginTime;
        this.imageCategoryId = imageCategoryId;
        this.passTime = passTime;
        this.list = list;
        this.reportDoctorGUID = reportDoctorGUID;
        this.accountType = accountType;
        this.packageValue = packageValue;
        this.pacsIp = pacsIp;
        this.bTime = bTime;
        this.eTime = eTime;
        this.carCount = carCount;
        this.longitude = longitude;
        this.latitude = latitude;
        this.operaType = operaType;
        this.lable = lable;
        this.staffId = staffId;
        this.deveName = deveName;
    }

    public String getGuId() {
        return guId;
    }

    public void setGuId(String guId) {
        this.guId = guId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getShopNames() {
        return shopNames;
    }

    public void setShopNames(String shopNames) {
        this.shopNames = shopNames;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getImageCategoryId() {
        return imageCategoryId;
    }

    public void setImageCategoryId(String imageCategoryId) {
        this.imageCategoryId = imageCategoryId;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getReportDoctorGUID() {
        return reportDoctorGUID;
    }

    public void setReportDoctorGUID(String reportDoctorGUID) {
        this.reportDoctorGUID = reportDoctorGUID;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getPacsIp() {
        return pacsIp;
    }

    public void setPacsIp(String pacsIp) {
        this.pacsIp = pacsIp;
    }

    public String getbTime() {
        return bTime;
    }

    public void setbTime(String bTime) {
        this.bTime = bTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String getCarCount() {
        return carCount;
    }

    public void setCarCount(String carCount) {
        this.carCount = carCount;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getOperaType() {
        return operaType;
    }

    public void setOperaType(String operaType) {
        this.operaType = operaType;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getDeveName() {
        return deveName;
    }

    public void setDeveName(String deveName) {
        this.deveName = deveName;
    }

}
