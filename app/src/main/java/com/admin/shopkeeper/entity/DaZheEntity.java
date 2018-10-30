package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/19 0019.
 * {
 * "Guid":"aa22a0de-bc53-45ae-a883-4f820cd5fa30",
 * "RESTAURANTID":"4b176f0e-0553-4094-8181-5048641b20ef",
 * "Count":"75",
 * "Name":"7.5折"
 * }
 */

public class DaZheEntity {
    private String Guid;

    @SerializedName("RESTAURANTID")
    private String restaurantId;

    @SerializedName("Count")
    private String count;

    @SerializedName("Name")
    private String name;

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
