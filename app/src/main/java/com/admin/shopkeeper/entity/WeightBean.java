package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/6.
 */

public class WeightBean implements Serializable {
    //{\"GUID\":\"529d0e2d-c21c-40cc-a799-a7f34f920c6a\",
    // \"Name\":\"签签\",
    // \"RESTAURANTID\":\"4b176f0e-0553-4094-8181-5048641b20ef\",
    // \"Price\":0.30,
    // \"Weight\":\"0.0021\",
    // \"Isopen\":\"1\",
    // \"Deviation\":\"0\",
    // \"State\":\"1\"},
    @SerializedName("GUID")
    String guId;
    @SerializedName("Name")
    String name;
    @SerializedName("RESTAURANTID")
    String restaurantId;
    @SerializedName("Price")
    String price;
    @SerializedName("Weight")
    String weight;
    @SerializedName("Isopen")
    String isopen;
    @SerializedName("Deviation")
    String deviation;
    @SerializedName("State")
    String state;

    public String getGuId() {
        return guId;
    }

    public void setGuId(String guId) {
        this.guId = guId;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen;
    }

    public String getDeviation() {
        return deviation;
    }

    public void setDeviation(String deviation) {
        this.deviation = deviation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
