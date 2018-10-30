package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/8.
 */

public class RoomTypeBean implements Serializable {

    //{\"Id\":\"562a0f2f-0ddf-48cb-8437-a97835765040\",
    // \"TypeName\":\"卡座\",
    // \"Status\":true,
    // \"SortNo\":null,
    // \"StoreId\":\"4c30e647-333d-4c61-96d3-5f7e34b0571d\"}

    @SerializedName("Id")
    private String id;
    @SerializedName("TypeName")
    private String typeName;
    @SerializedName("Status")
    private boolean status;
    @SerializedName("SortNo")
    private int sortno;
    @SerializedName("StoreId")
    private String stroeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getSortno() {
        return sortno;
    }

    public void setSortno(int sortno) {
        this.sortno = sortno;
    }

    public String getStroeId() {
        return stroeId;
    }

    public void setStroeId(String stroeId) {
        this.stroeId = stroeId;
    }
}
