package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * {
 * {\"PRODUCTNAME\":\"回锅肉\",
 * \"TOTALPRICE\":28.00,
 * \"COUNT\":1.00,
 * \"INPUTTIME\":\"2017-10-31 10:41:08\",
 * \"USERNAME\":\"收银\",\"Type\":\"0\",
 * \"BILLID\":\"f3580e3d-8bb6-4d80-88ba-70c19d5a480e\"}
 * }
 */

public class OrderManageDetail implements Serializable {
    @SerializedName("BILLID")
    private String id;
    @SerializedName("PRODUCTNAME")
    private String productName;
    @SerializedName("TOTALPRICE")
    private String totalPrice;
    @SerializedName("COUNT")
    private String count;
    @SerializedName("INPUTTIME")
    private String inputTime;
    @SerializedName("USERNAME")
    private String userNmae;

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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInputTime() {
        return inputTime;
    }

    public void setInputTime(String inputTime) {
        this.inputTime = inputTime;
    }

    public String getUserNmae() {
        return userNmae;
    }

    public void setUserNmae(String userNmae) {
        this.userNmae = userNmae;
    }
}
