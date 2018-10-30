package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/30.
 */

public class FoodBean implements Serializable {

    //{\"PRODUCTID\":\"498d601a-1908-4391-9c6a-31a51463c372\",
    // \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
    // \"ID\":\"303\",
    // \"PRODUCTNAME\":\"鸡杂米线\",
    // \"PINYIN\":\"鸡杂米线jzmx303\",
    // \"UNIT\":\"份\",
    // \"MINUNIT\":\"1\",
    // \"PRODUCTTYPEID\":\"00c247e9-0935-4c56-99ce-4513d22fd233\",
    // \"PRODUCTTYPENAME\":\"普通菜品\",
    // \"PRICE\":\"10.0\",
    // \"PRODUCTFile\":\"1504862450101.jpg\",
    // \"PRODUCTIMAGE\":null,
    // \"STATE\":1,
    // \"REMARK\":\"\",
    // \"TasteID\":\"\",
    // \"IsDaZhe\":null,
    // \"DaZhe\":null,
    // \"WarCount\":\"0\",
    // \"IsClose\":\"0\",
    // \"IsCloseName\":null,
    // \"ProductCount\":0,
    // \"ChuCaiType\":\"0\",
    // \"CanDiscount\":0,
    // \"MemberPice\":0.00,
    // \"SalesType\":1,\
    // "AccordIng\":\"0\",
    // \"ProtuctShuXing\":\"2\",
    // \"ProductGive\":\"0\",
    // \"TasteType\":\"0\",
    // \"PrintWay\":\"0\",
    // \"PIRNTID\":\"10219\",
    // \"Name\":\"交班打印机\"},
    @SerializedName("TasteID")
    private String tasteId;
    @SerializedName("IsDaZhe")
    private boolean isDazhe;
    @SerializedName("DaZhe")
    private String dazhe;
    @SerializedName("WarCount")
    private String warCount;
    @SerializedName("IsClose")
    private String isClose;
    @SerializedName("IsCloseName")
    private String isCloseName;
    @SerializedName("ProductCount")
    private int productCount;
    @SerializedName("ChuCaiType")
    private String chuCaiType;
    @SerializedName("CanDiscount")
    private int canDiscount;
    @SerializedName("MemberPice")
    private double memberPice;
    @SerializedName("SalesType")
    private int salesType;
    @SerializedName("AccordIng")
    private String accordIng;
    @SerializedName("ProtuctShuXing")
    private String protuctShuXing;
    @SerializedName("ProductGive")
    private String productGive;
    @SerializedName("TasteType")
    private String tasteType;
    @SerializedName("PrintWay")
    private String printWay;
    @SerializedName("PIRNTID")
    private String printId;
    @SerializedName("Name")
    private String name;
    @SerializedName(value = "PRODUCTID", alternate = {"ProductID"})
    String productId;
    @SerializedName("RESTAURANTID")
    String restaurantId;
    @SerializedName("ID")
    String id;
    @SerializedName(value = "PRODUCTNAME", alternate = {"ProductName"})
    String productName;
    @SerializedName("PINYIN")
    String pinyin;
    @SerializedName("UNIT")
    String unit;
    @SerializedName("MINUNIT")
    String minunit;
    @SerializedName("PRODUCTTYPEID")
    String productTypeId;
    @SerializedName("PRODUCTTYPENAME")
    String productTypeName;
    @SerializedName("PRICE")
    double price;
    @SerializedName("PRODUCTIMAGE")
    private String productImage;
    @SerializedName("STATE")
    int state;
    @SerializedName("REMARK")
    String remark;
    @SerializedName("PRODUCTFile")
    private String productFile;

    int count;
    boolean delete;

    boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTasteId() {
        return tasteId;
    }

    public void setTasteId(String tasteId) {
        this.tasteId = tasteId;
    }

    public boolean isDazhe() {
        return isDazhe;
    }

    public void setDazhe(boolean dazhe) {
        isDazhe = dazhe;
    }

    public String getDazhe() {
        return dazhe;
    }

    public void setDazhe(String dazhe) {
        this.dazhe = dazhe;
    }

    public String getWarCount() {
        return warCount;
    }

    public void setWarCount(String warCount) {
        this.warCount = warCount;
    }

    public String getIsClose() {
        return isClose;
    }

    public void setIsClose(String isClose) {
        this.isClose = isClose;
    }

    public String getIsCloseName() {
        return isCloseName;
    }

    public void setIsCloseName(String isCloseName) {
        this.isCloseName = isCloseName;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getChuCaiType() {
        return chuCaiType;
    }

    public void setChuCaiType(String chuCaiType) {
        this.chuCaiType = chuCaiType;
    }

    public int getCanDiscount() {
        return canDiscount;
    }

    public void setCanDiscount(int canDiscount) {
        this.canDiscount = canDiscount;
    }

    public double getMemberPice() {
        return memberPice;
    }

    public void setMemberPice(double memberPice) {
        this.memberPice = memberPice;
    }

    public int getSalesType() {
        return salesType;
    }

    public void setSalesType(int salesType) {
        this.salesType = salesType;
    }

    public String getAccordIng() {
        return accordIng;
    }

    public void setAccordIng(String accordIng) {
        this.accordIng = accordIng;
    }

    public String getProtuctShuXing() {
        return protuctShuXing;
    }

    public void setProtuctShuXing(String protuctShuXing) {
        this.protuctShuXing = protuctShuXing;
    }

    public String getProductGive() {
        return productGive;
    }

    public void setProductGive(String productGive) {
        this.productGive = productGive;
    }

    public String getTasteType() {
        return tasteType;
    }

    public void setTasteType(String tasteType) {
        this.tasteType = tasteType;
    }

    public String getPrintWay() {
        return printWay;
    }

    public void setPrintWay(String printWay) {
        this.printWay = printWay;
    }

    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductFile() {
        return productFile;
    }

    public void setProductFile(String productFile) {
        this.productFile = productFile;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMinunit() {
        return minunit;
    }

    public void setMinunit(String minunit) {
        this.minunit = minunit;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
