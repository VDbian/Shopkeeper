package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/26.
 */

public class ChainBean implements Serializable {
    //{\"MerchantIDXia\":\"3956daa1-9a45-4631-9faf-e6b5f5943e87\",\"Names\":\"谈豆花\"}
    @SerializedName("MerchantIDXia")
    String merchantId;
    @SerializedName("Names")
    String names;

    boolean select;

    public ChainBean() {

    }

    public ChainBean(String id, String name) {
        this.merchantId = id;
        this.names = name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
