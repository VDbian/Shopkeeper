package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/26.
 */

public class WeixinOrderBean implements Serializable {
    //\"zenupice\":\"0\",
    // \"memberpice\":\"0\",
    // \"mansonpice\":\"0\",
    // \"kaquanpice\":\"0\",
    // \"yufupice\":\"128.00\",
//\"DistancePice\":null,
// \"PackagPice\":null,
// \"canju\":null,
// \"youhui\":\"128\",
// \"yuanjia\":\"128.00\",
// \"yintui\":\"0\",
// \"yinfu\":\"0\"
// memberpiceNew
//memberzenupice
// }
    @SerializedName("zenupice")
    private double zenupice;
    @SerializedName("memberpice")
    private double memberpice;
    @SerializedName("mansonpice")
    private double mansonpice;
    @SerializedName("kaquanpice")
    private double kaquanpice;
    @SerializedName("yufupice")
    private double yufupice;
    @SerializedName("DistancePice")
    private double distancePice;
    @SerializedName("PackagPice")
    private double packagPice;
    @SerializedName("canju")
    private double canju;
    @SerializedName("youhui")
    private double youhui;
    @SerializedName("yuanjia")
    private double yuanjia;
    @SerializedName("yintui")
    private double yintui;
    @SerializedName("yinfu")
    private double yinfu;
    @SerializedName("memberpiceNew")
    private double memberpiceNew;
    @SerializedName("memberzenupice")
    private double memberzenupice;

    public double getMemberzenupice() {
        return memberzenupice;
    }

    public void setMemberzenupice(double memberzenupice) {
        this.memberzenupice = memberzenupice;
    }

    public double getMemberpiceNew() {
        return memberpiceNew;
    }

    public void setMemberpiceNew(double memberpiceNew) {
        this.memberpiceNew = memberpiceNew;
    }

    public double getZenupice() {
        return zenupice;
    }

    public void setZenupice(double zenupice) {
        this.zenupice = zenupice;
    }

    public double getMemberpice() {
        return memberpice;
    }

    public void setMemberpice(double memberpice) {
        this.memberpice = memberpice;
    }

    public double getMansonpice() {
        return mansonpice;
    }

    public void setMansonpice(double mansonpice) {
        this.mansonpice = mansonpice;
    }

    public double getKaquanpice() {
        return kaquanpice;
    }

    public void setKaquanpice(double kaquanpice) {
        this.kaquanpice = kaquanpice;
    }

    public double getYufupice() {
        return yufupice;
    }

    public void setYufupice(double yufupice) {
        this.yufupice = yufupice;
    }

    public double getDistancePice() {
        return distancePice;
    }

    public void setDistancePice(double distancePice) {
        this.distancePice = distancePice;
    }

    public double getPackagPice() {
        return packagPice;
    }

    public void setPackagPice(double packagPice) {
        this.packagPice = packagPice;
    }

    public double getCanju() {
        return canju;
    }

    public void setCanju(double canju) {
        this.canju = canju;
    }

    public double getYouhui() {
        return youhui;
    }

    public void setYouhui(double youhui) {
        this.youhui = youhui;
    }

    public double getYuanjia() {
        return yuanjia;
    }

    public void setYuanjia(double yuanjia) {
        this.yuanjia = yuanjia;
    }

    public double getYintui() {
        return yintui;
    }

    public void setYintui(double yintui) {
        this.yintui = yintui;
    }

    public double getYinfu() {
        return yinfu;
    }

    public void setYinfu(double yinfu) {
        this.yinfu = yinfu;
    }
}
