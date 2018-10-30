package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2017/11/1.
 */

public class GuizeBean implements Serializable {

    //{\"GUID\":\"133ef70d-4d79-4fa2-bd15-0b8ab2ed5090\",\"PiCi\":\"171102173426621219\",
    // \"Type\":\"6\",\"TiaoJian\":null,\"Price\":null,\"Btime\":\"2017-11-02 00:00:00\",\"Etime\":\"2017-11-02 00:00:00\"}

    @SerializedName("GUID")
    String guid;
    @SerializedName("PiCi")
    String pici;
    @SerializedName("TiaoJian")
    String type;
    @SerializedName("Price")
    String value;
    @SerializedName("Btime")
    String start;
    @SerializedName("Etime")
    String end;
    @SerializedName("Type")
    String name;

    public GuizeBean() {

    }

    public static GuizeBean getVoidBean() {
        return new GuizeBean("", "", "", "");
    }

    public GuizeBean(String type, String value, String start, String end) {
        this.type = type;
        this.value = value;
        this.start = start;
        this.end = end;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPici() {
        return pici;
    }

    public void setPici(String pici) {
        this.pici = pici;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
