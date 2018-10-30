package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/4.
 */

public class PrintBean implements Serializable{
    //{\"ShangJia\":\"智慧餐厅\",
    // \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
    // \"NAME\":\"后厨打印机\",
    // \"IPADDRESS\":\"192.168.0.252\",
    // \"PORT\":\"1433\",
    // \"ID\":3150},
    //
    //{\"ShangJia\":\"智慧餐厅\",\"ID\":10219,\"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
//\"NAME\":\"交班打印机\",\"IPADDRESS\":\"192.168.0.251\",\"PORT\":\"1435\",

//\"PRINTTYPE\":\"5\",\"QieDao\":\"1\",\"Types\":\"2\",\"CutType\":\"0\",
//\"PrintCount\":1,\"PrintSpec\":\"0\",\"PrintWay\":\"1\"},
    @SerializedName("ID")
    String id;
    @SerializedName("PORT")
    String port;
    @SerializedName("IPADDRESS")
    String ipAddress;
    @SerializedName("NAME")
    String name;
    @SerializedName("RESTAURANTID")
    String restaurantId;
    @SerializedName("ShangJia")
    String shangjia;
    @SerializedName("PRINTTYPE")
    private int printType;
    @SerializedName("QieDao")
    private String qiedao;
    @SerializedName("Types")
    private int types;
    @SerializedName("CutType")
    private String cutType;
    @SerializedName("PrintCount")
    private int printCount;
    @SerializedName("PrintSpec")
    private String printSpec;
    @SerializedName("PrintWay")
    private int printWay;

    public int getPrintType() {
        return printType;
    }

    public void setPrintType(int printType) {
        this.printType = printType;
    }

    public String getQiedao() {
        return qiedao;
    }

    public void setQiedao(String qiedao) {
        this.qiedao = qiedao;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public String getCutType() {
        return cutType;
    }

    public void setCutType(String cutType) {
        this.cutType = cutType;
    }

    public int getPrintCount() {
        return printCount;
    }

    public void setPrintCount(int printCount) {
        this.printCount = printCount;
    }

    public String getPrintSpec() {
        return printSpec;
    }

    public void setPrintSpec(String printSpec) {
        this.printSpec = printSpec;
    }

    public int getPrintWay() {
        return printWay;
    }

    public void setPrintWay(int printWay) {
        this.printWay = printWay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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

    public String getShangjia() {
        return shangjia;
    }

    public void setShangjia(String shangjia) {
        this.shangjia = shangjia;
    }
}
