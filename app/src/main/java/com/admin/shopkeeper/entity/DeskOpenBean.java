package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/26.
 */

public class DeskOpenBean implements Serializable {
    @SerializedName("Name")
    String name;
    @SerializedName("PersonCount")
    int personCount;
    @SerializedName("ShangZuo")
    double shangzuo;
    @SerializedName("KaiTai")
    double kaitai;
    @SerializedName("FanTai")
    double fantai;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public double getShangzuo() {
        return shangzuo;
    }

    public void setShangzuo(double shangzuo) {
        this.shangzuo = shangzuo;
    }

    public double getKaitai() {
        return kaitai;
    }

    public void setKaitai(double kaitai) {
        this.kaitai = kaitai;
    }

    public double getFantai() {
        return fantai;
    }

    public void setFantai(double fantai) {
        this.fantai = fantai;
    }
}
