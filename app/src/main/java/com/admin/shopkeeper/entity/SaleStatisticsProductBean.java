package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * {\"PRODUCTTYPENAME\":\"本店特色饺\",\"PRODUCTTYPEID\":\"15a43776-b53f-46ff-beb0-e4ce76211de2\"}
 *
 */

public class SaleStatisticsProductBean implements Serializable {

    @SerializedName("PRODUCTTYPENAME")
    private String productName;
    @SerializedName("PRODUCTTYPEID")
    private String productId;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
