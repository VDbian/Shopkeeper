package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/18.
 */

public class WechatBean implements Serializable {
//{\"GUID\":\"a6a70894-58e0-4826-987c-0ca07819ead7\",
// \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
// \"PersonCenter\":3,
// \"WeiXinOrder\":1,
// \"WeiXinYuDin\":\"2\",
// \"WeiXinWaiMai\":\"2\",
// \"WeiXinKuaiCan\":\"1\",
// \"TanDian\":\"1\"}"\"EnableCenter\":\"1\",\"EnableOrder\":\"1\"

    //{\"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
    // \"Days\":7,
    // \"Prepaid\":20.00,
    // \"QrCodePay\":\"0\",
    // \"Functions\":\"2,1,3,5\",
    // \"FoodOrDes\":\"1,2\",
    // \"WeiXinKuaiCan\":\"1,2,4\",
    // \"WeiXinWaiMai\":\"1,2,4\",\
    // "WeiXinYuDin\":\"1,2,4\",
    // \"TanDian\":\"1,2,4\"

    @SerializedName("Days")
    int days;
    @SerializedName("Prepaid")
    double prepaid;
    @SerializedName("QrCodePay")
    String qrcodePay;
    @SerializedName("Functions")
    String functions;
    @SerializedName("FoodOrDes")
    String foodOrDes;
    @SerializedName("GUID")
    private String guid;
    @SerializedName("RESTAURANTID")
    private String shopId;
    @SerializedName("PersonCenter")
    private double personCenter;
    @SerializedName("WeiXinOrder")
    private double weixinOrder;
    @SerializedName("WeiXinYuDin")
    private String weixinYuding;
    @SerializedName("WeiXinWaiMai")
    private String weixinWaimai;
    @SerializedName("WeiXinKuaiCan")
    private String weixinKuaican;
    @SerializedName("TanDian")
    private String tandian;
    @SerializedName("EnableCenter")
    private String jifenAdding;
    @SerializedName("EnableOrder")
    private String jifenExchange;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(double prepaid) {
        this.prepaid = prepaid;
    }

    public String getQrcodePay() {
        return qrcodePay;
    }

    public void setQrcodePay(String qrcodePay) {
        this.qrcodePay = qrcodePay;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public String getFoodOrDes() {
        return foodOrDes;
    }

    public void setFoodOrDes(String foodOrDes) {
        this.foodOrDes = foodOrDes;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public double getPersonCenter() {
        return personCenter;
    }

    public void setPersonCenter(double personCenter) {
        this.personCenter = personCenter;
    }

    public double getWeixinOrder() {
        return weixinOrder;
    }

    public void setWeixinOrder(double weixinOrder) {
        this.weixinOrder = weixinOrder;
    }

    public String getWeixinYuding() {
        return weixinYuding;
    }

    public void setWeixinYuding(String weixinYuding) {
        this.weixinYuding = weixinYuding;
    }

    public String getWeixinWaimai() {
        return weixinWaimai;
    }

    public void setWeixinWaimai(String weixinWaimai) {
        this.weixinWaimai = weixinWaimai;
    }

    public String getWeixinKuaican() {
        return weixinKuaican;
    }

    public void setWeixinKuaican(String weixinKuaican) {
        this.weixinKuaican = weixinKuaican;
    }

    public String getTandian() {
        return tandian;
    }

    public void setTandian(String tandian) {
        this.tandian = tandian;
    }

    public String getJifenAdding() {
        return jifenAdding;
    }

    public void setJifenAdding(String jifenAdding) {
        this.jifenAdding = jifenAdding;
    }

    public String getJifenExchange() {
        return jifenExchange;
    }

    public void setJifenExchange(String jifenExchange) {
        this.jifenExchange = jifenExchange;
    }
}
