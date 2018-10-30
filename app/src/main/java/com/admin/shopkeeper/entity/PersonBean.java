package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/8.
 */

public class PersonBean implements Serializable {
    //\"GUID\":\"53e866bf-ee86-4994-9d16-43f9ab3d5db8\",\"Name\":\"1-5人\",\"Remark\":null}
    @SerializedName("GUID")
    private String guid;
    @SerializedName("Name")
    private String name;
    @SerializedName("Remark")
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
