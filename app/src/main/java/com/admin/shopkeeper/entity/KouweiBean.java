package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/11.
 */

public class KouweiBean implements Serializable {
    //{\"GUID\":\"c900fc83-55e1-4ca0-ba7a-9a76eb005b6e\",
    // \"ProtuctID\":\"498d601a-1908-4391-9c6a-31a51463c372\",
    // \"Name\":\"哈哈\",
    // \"No\":\"11\",
    // \"RESTAURANTID\":\"4B176F0E-0553-4094-8181-5048641B20EF\"}
    @SerializedName("GUID")
    private String guid;
    @SerializedName("No")
    private String no;
    @SerializedName("Name")
    private String name;
    @SerializedName("ProtuctID")
    private String productId;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
