package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/26.
 */

public class FreeBean implements Serializable {
    @SerializedName("Names")
    String names;
    @SerializedName("FREEMONEY")
    double freeMoney;
    @SerializedName("Operate")
    String oprate;
    @SerializedName("RESTAURANTID")
    String shopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public double getFreeMoney() {
        return freeMoney;
    }

    public void setFreeMoney(double freeMoney) {
        this.freeMoney = freeMoney;
    }

    public String getOprate() {
        return oprate;
    }

    public void setOprate(String oprate) {
        this.oprate = oprate;
    }
}
