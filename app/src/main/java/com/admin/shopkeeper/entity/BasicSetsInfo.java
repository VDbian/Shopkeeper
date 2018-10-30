package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 老板端用户信息
 * {
 "GUID\":\"1fe62a05-c797-43a9-86c7-82081e6b7a67\",
 \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
 \"PrintSet\":\"0\"
 ,\"PayImage\":\"\"
 ,\"ProductSize\":\"2\"
 ,\"PayType\":\"3\"
 ,\"GuestShow\":\"0\",
 \"ChenJinDaZhe\":\"1\"
 ,\"JieZhangPay\":\"1\"}
 * }
 */

public class BasicSetsInfo implements Serializable {
    @SerializedName("GUID")
    private String guId;
    //开单是否打印  1 是 2否
    @SerializedName("PrintSet")
    private String printSet;
    @SerializedName("PayImage")
    private String payImage;

    @SerializedName("RESTAURANTID")
    private String restaurantID;
    //0小 1中 2大
    @SerializedName("ProductSize")
    private String productSize;
    //快速结账支付方式 现金1 银行卡2 会员卡5 微信支付3 线下支付宝6 线下微信7
    @SerializedName("PayType")
    private String payType;
    //客显 1 是 2否
    @SerializedName("GuestShow")
    private String guestShow;
    //称斤打折 1是2否
    @SerializedName("ChenJinDaZhe")
    private String chenJinDazhe;

    @SerializedName("JieZhangPay")
    private int jieZhangPay;


    public BasicSetsInfo() {
    }

    public BasicSetsInfo(String guId, String printSet, String payImage, String restaurantID, String productSize, String payType, String guestShow, String chenJinDazhe, int jieZhangPay) {
        this.guId = guId;
        this.printSet = printSet;
        this.payImage = payImage;
        this.restaurantID = restaurantID;
        this.productSize = productSize;
        this.payType = payType;
        this.guestShow = guestShow;
        this.chenJinDazhe = chenJinDazhe;
        this.jieZhangPay = jieZhangPay;
    }

    public String getGuId() {
        return guId;
    }

    public void setGuId(String guId) {
        this.guId = guId;
    }

    public String getPrintSet() {
        return printSet;
    }

    public void setPrintSet(String printSet) {
        this.printSet = printSet;
    }

    public String getPayImage() {
        return payImage;
    }

    public void setPayImage(String payImage) {
        this.payImage = payImage;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getGuestShow() {
        return guestShow;
    }

    public void setGuestShow(String guestShow) {
        this.guestShow = guestShow;
    }

    public String getChenJinDazhe() {
        return chenJinDazhe;
    }

    public void setChenJinDazhe(String chenJinDazhe) {
        this.chenJinDazhe = chenJinDazhe;
    }

    public int getJieZhangPay() {
        return jieZhangPay;
    }

    public void setJieZhangPay(int jieZhangPay) {
        this.jieZhangPay = jieZhangPay;
    }
}
