package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/11.
 */

public class BasicSetBean implements Serializable {
    //{\"GUID\":\"1fe62a05-c797-43a9-86c7-82081e6b7a67\",
    // \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
    // \"PrintSet\":\"1\",
    // \"PayImage\":\"老板.jpg\",
    // \"ProductSize\":\"1\",
    // \"PayType\":\"5\",
    // \"GuestShow\":\"1\",
    // \"ChenJinDaZhe\":\"1\",
    // \"JieZhangPay\":\"2\" PayPassWord:null
//    "CashPayType\":\"1,2,5,3,7,4,8,9,10\"
//    "UniFiedPice\":\"0\",\"IsChan\":\"1\"
    //\"Numberingrules\":\"2\",\"CountCoding\":\"50\",\"ChooseProduct\":


    @SerializedName("GUID")
    private String guid;
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    @SerializedName("PrintSet")
    private String printSet;
    @SerializedName("PayImage")
    private String payImage;
    @SerializedName("ProductSize")
    private String productSize;
    @SerializedName("PayType")
    private String payType;
    @SerializedName("GuestShow")
    private String guestShow;
    @SerializedName("ChenJinDaZhe")
    private String chenjinDazhe;
    @SerializedName("JieZhangPay")
    private String jiezhangPay;
    @SerializedName("PayPassWord")
    private String payPassord;
    @SerializedName("CashPayType")
    private String cashPayType;
    @SerializedName("UniFiedPice")
    private String uniFiedPice;//统一支付
    @SerializedName("IsChan")
    private String isChan;//会员通用
    @SerializedName("Numberingrules")
    private String numeralRule;//打印排号规则
    @SerializedName("CountCoding")
    private String numeralNumber;//循环起始编码
    @SerializedName("ChooseProduct")
    private String chooseProduct;//选择商品id

    public String getNumeralRule() {
        return numeralRule;
    }

    public void setNumeralRule(String numeralRule) {
        this.numeralRule = numeralRule;
    }

    public String getNumeralNumber() {
        return numeralNumber;
    }

    public void setNumeralNumber(String numeralNumber) {
        this.numeralNumber = numeralNumber;
    }

    public String getChooseProduct() {
        return chooseProduct;
    }

    public void setChooseProduct(String chooseProduct) {
        this.chooseProduct = chooseProduct;
    }

    public String getUniFiedPice() {
        return uniFiedPice;
    }

    public void setUniFiedPice(String uniFiedPice) {
        this.uniFiedPice = uniFiedPice;
    }

    public String getIsChan() {
        return isChan;
    }

    public void setIsChan(String isChan) {
        this.isChan = isChan;
    }

    public String getCashPayType() {
        return cashPayType;
    }

    public void setCashPayType(String cashPayType) {
        this.cashPayType = cashPayType;
    }

    public String getPayPassord() {
        return payPassord;
    }

    public void setPayPassord(String payPassord) {
        this.payPassord = payPassord;
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

    public String getChenjinDazhe() {
        return chenjinDazhe;
    }

    public void setChenjinDazhe(String chenjinDazhe) {
        this.chenjinDazhe = chenjinDazhe;
    }

    public String getJiezhangPay() {
        return jiezhangPay;
    }

    public void setJiezhangPay(String jiezhangPay) {
        this.jiezhangPay = jiezhangPay;
    }
}
