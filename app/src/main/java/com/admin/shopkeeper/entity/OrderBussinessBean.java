package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/7.
 */

public class OrderBussinessBean implements Serializable {

    //Man 满送优惠
    //Frees 卡券/积分
    //        member会员优惠
    //Per 权限优惠
    //Yufu 预付金额
    //Total 总金额
    //Free 优惠金额
    //Pice  应收金额
    @SerializedName("Type")
    private String type;
    @SerializedName("man")
    private double man;
    @SerializedName("frees")
    private double frees;
    @SerializedName("member")
    private double member;
    @SerializedName("per")
    private double per;
    @SerializedName("yufu")
    private double yufu;
    @SerializedName("totle")
    private double totle;
    @SerializedName("free")
    private double free;
    @SerializedName("pice")
    private double price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMan() {
        return man;
    }

    public void setMan(double man) {
        this.man = man;
    }

    public double getFrees() {
        return frees;
    }

    public void setFrees(double frees) {
        this.frees = frees;
    }

    public double getMember() {
        return member;
    }

    public void setMember(double member) {
        this.member = member;
    }

    public double getPer() {
        return per;
    }

    public void setPer(double per) {
        this.per = per;
    }

    public double getYufu() {
        return yufu;
    }

    public void setYufu(double yufu) {
        this.yufu = yufu;
    }

    public double getTotle() {
        return totle;
    }

    public void setTotle(double totle) {
        this.totle = totle;
    }

    public double getFree() {
        return free;
    }

    public void setFree(double free) {
        this.free = free;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
