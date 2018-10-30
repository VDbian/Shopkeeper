package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/11.
 */

public class DepartmentBean implements Serializable {

    /**
     * {\"ID\":\"1f631143-4245-42a2-b25c-63988b18e0db\",
     * \"RESTAURANTID\":\"cbd05ab9-419c-4bf7-8ff2-a0ae14ca5e87\",
     * \"NAME\":\"餐饮部\",
     * \"PARENT\":\"24ebbfdc-30c0-47d3-81cc-e0fe2815bd14\",
     * \"STATE\":1}
     */
    @SerializedName("ID")
    private String id;
    @SerializedName("RESTAURANTID")
    private String restaurantId;
    @SerializedName("NAME")
    private String name;
    @SerializedName("PARENT")
    private String parent;
    @SerializedName("STATE")
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
