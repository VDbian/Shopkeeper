package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/5 0005.
 * "RESTAURANTID":"4B176F0E-0553-4094-8181-5048641B20EF",
 * "OrderID":"72c6c33f-4ac7-479c-b726-19ab005c8e9b",
 * "ID":"80123f48-fee3-44d0-a2f4-01c1eb2c4414",
 * "ProductNmae":"五香兔头",
 * "Ammount":1,
 * "MemberPrice":8,
 * "Price":8
 * CanDiscount 1 减免
 */
public class OrderDetailFood implements Serializable {
    @SerializedName("RESTAURANTID")
    private String restaurantID;
    @SerializedName("OrderID")
    private String orderID;
    @SerializedName("ID")
    private String id;
    @SerializedName("ProductNmae")
    private String productNmae;
    @SerializedName("Ammount")
    private double Ammount;
    @SerializedName("MemberPrice")
    private double memberPrice;
    @SerializedName("Price")
    private double price;
    @SerializedName("DETAILID")
    private String detailId;
    @SerializedName("CanDiscount")
    private int canDiscount;
    @SerializedName("SeasonID")
    private String seasonID;
    @SerializedName("SeasonName")
    private String seasonName;
    @SerializedName("SeasonPrice")
    private double seasonPrice;
    @SerializedName("SeasonSum")
    private String seasonSum;
    @SerializedName("Giving")
    private int giving;
    @SerializedName("PRODUCTSHUXIN")
    private String productshuxin;
    @SerializedName("PRODUCTSHUXINID")
    private String productshuxingId;
    @SerializedName("REMARK")
    private String remark;
    @SerializedName("REMARKID")
    private String remarkId;
    @SerializedName("CHARGEMONEY")
    private String chargeMoney;
    @SerializedName("Weight")
    private double weight;
    @SerializedName("COUNT")
    private int count;
    private int sale;

    @SerializedName("UNIT")
    private String unit;


    public String getSeasonSum() {
        return seasonSum;
    }

    public void setSeasonSum(String seasonSum) {
        this.seasonSum = seasonSum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(String chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(String remarkId) {
        this.remarkId = remarkId;
    }

    public String getProductshuxin() {
        return productshuxin;
    }

    public void setProductshuxin(String productshuxin) {
        this.productshuxin = productshuxin;
    }

    public String getProductshuxingId() {
        return productshuxingId;
    }

    public void setProductshuxingId(String productshuxingId) {
        this.productshuxingId = productshuxingId;
    }

    public String getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(String seasonID) {
        this.seasonID = seasonID;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public double getSeasonPrice() {
        return seasonPrice;
    }

    public void setSeasonPrice(double seasonPrice) {
        this.seasonPrice = seasonPrice;
    }

    public int getGiving() {
        return giving;
    }

    public void setGiving(int giving) {
        this.giving = giving;
    }

    public int getCanDiscount() {
        return canDiscount;
    }

    public void setCanDiscount(int canDiscount) {
        this.canDiscount = canDiscount;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNmae() {
        return productNmae;
    }

    public void setProductNmae(String productNmae) {
        this.productNmae = productNmae;
    }

    public double getAmmount() {
        return Ammount;
    }

    public void setAmmount(double ammount) {
        Ammount = ammount;
    }

    public double getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(double memberPrice) {
        this.memberPrice = memberPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDetailFood{" +
                "restaurantID='" + restaurantID + '\'' +
                ", orderID='" + orderID + '\'' +
                ", id='" + id + '\'' +
                ", productNmae='" + productNmae + '\'' +
                ", Ammount=" + Ammount +
                ", memberPrice=" + memberPrice +
                ", price=" + price +
                ", detailId='" + detailId + '\'' +
                ", canDiscount=" + canDiscount +
                ", seasonID='" + seasonID + '\'' +
                ", seasonName='" + seasonName + '\'' +
                ", seasonPrice=" + seasonPrice +
                ", seasonSum='" + seasonSum + '\'' +
                ", giving=" + giving +
                ", productshuxin='" + productshuxin + '\'' +
                ", productshuxingId='" + productshuxingId + '\'' +
                ", remark='" + remark + '\'' +
                ", remarkId='" + remarkId + '\'' +
                ", chargeMoney='" + chargeMoney + '\'' +
                ", weight=" + weight +
                ", count=" + count +
                ", sale=" + sale +
                ", unit='" + unit + '\'' +
                '}';
    }
}
