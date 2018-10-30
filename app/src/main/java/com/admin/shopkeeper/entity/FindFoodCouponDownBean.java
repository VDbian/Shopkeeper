package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/7.
 */

public class FindFoodCouponDownBean implements Serializable {

    //\"GUID\":\"46d20a4f-be70-4c78-b5b1-55f05f1b0508\",
    // \"BILLDAZHEID\":\"5141ff7a-9503-4681-a76d-217538d75e81\",
    // \"PRODUCTID\":\"7a625efb-bf26-426e-93fa-a1b93c2bc5c7\",
    // \"PRODUCTNAME\":\"牛肉汤\",
    // \"RESTAURANTID\":\"4B176F0E-0553-4094-8181-5048641B20EF\",
    // \"TYPE\":\"1\",
    // \"PRODUCTTYPEID\":\"b2a18ce8-c65c-4e18-880e-d97c55bc5c2e\",
    // \"PRODUCTTYPENAME\":\"输入数量菜品\"}
    @SerializedName("GUID")
    private String guID;
    @SerializedName("BILLDAZHEID")
    private String billedDaZheId;
    @SerializedName("PRODUCTID")
    private String productId;
    @SerializedName("PRODUCTNAME")
    private String productName;
    @SerializedName("RESTAURANTID")
    private String shopId;
    @SerializedName("TYPE")
    private String type;
    @SerializedName("PRODUCTTYPEID")
    private String productTypeId;
    @SerializedName("PRODUCTTYPENAME")
    private String productTypeName;

    public String getGuID() {
        return guID;
    }

    public void setGuID(String guID) {
        this.guID = guID;
    }

    public String getBilledDaZheId() {
        return billedDaZheId;
    }

    public void setBilledDaZheId(String billedDaZheId) {
        this.billedDaZheId = billedDaZheId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
