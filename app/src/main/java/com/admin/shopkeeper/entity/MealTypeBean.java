package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/30.
 * 套餐类型
 */

public class MealTypeBean implements Serializable{

    /**
     *
     {\"GUID\":\"5DCC27B5-5ECA-43FA-8166-4E481FE0783A\",
     \"ProductTypeName\":\"套餐\",
     \"SortNum\":1,
     \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
     \"flag\":0}
     */
    @SerializedName("GUID")
    private String guId;
    @SerializedName("ProductTypeName")
    private String productTypeName;
    @SerializedName("SortNum")
    private int sortNum;
    @SerializedName("RESTAURANTID")
    private String shopId;
    @SerializedName("flag")
    private int flag;

    public String getGuId() {
        return guId;
    }

    public void setGuId(String guId) {
        this.guId = guId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
