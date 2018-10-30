package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2017/10/31.
 */

public class CouponLineDownBean implements Serializable {
    /*
    {\"GUID\":\"005fdc51-680e-46d5-95ef-5d366bb94659\",
    \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
    \"Name\":\"测试1\",
    \"Counts\":100,
    \"MaxCout\":10,
    \"Pice\":20.00,
    \"State\":\"1\"
    MerchantName}
     */
    @SerializedName("GUID")
    String guid;
    @SerializedName("Name")
    String name;
    @SerializedName("Counts")
    int counts;
    @SerializedName("MaxCout")
    int maxUseCount;
    @SerializedName("Pice")
    double pice;
    @SerializedName("State")
    String state;
    @SerializedName("RESTAURANTID")
    String shopId;

    @SerializedName("MerchantName")
    String merchantName;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getMaxUseCount() {
        return maxUseCount;
    }

    public void setMaxUseCount(int maxUseCount) {
        this.maxUseCount = maxUseCount;
    }

    public double getPice() {
        return pice;
    }

    public void setPice(double pice) {
        this.pice = pice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
