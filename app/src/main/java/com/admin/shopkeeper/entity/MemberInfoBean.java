package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 商家权限管理名单
 * {
 * "ID\":\"0101beb1-1f11-4e39-bda3-bd75876022f1\",
 *\"StaffDepart\":null,
 *\"StaffCard\":null,
 *\"StaffName\":\"A\u0026琴先森\"
 *\"StaffTel\":null,
 *"StaffEmail\":null,
 *"StaffAddTime\":null
 * }
 */

public class MemberInfoBean implements Serializable {
    //会员微信名
    @SerializedName("StaffName")
    private String memberName;
    //会员名字
    @SerializedName("StaffDepart")
    private String memberDepart;
    //会员卡号
    @SerializedName("StaffCard")
    private String memberCard;
    //会员ID
    @SerializedName("ID")
    private String id;

    @SerializedName("StaffTel")
    private String memberTel;

    @SerializedName("StaffAddTime")
    private String memberAddTime;

    @SerializedName("StaffEmail")
    private String memberEmail;
    //序号
    private int position;
    //状态
    private int state;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public MemberInfoBean() {
    }

    public MemberInfoBean(String memberName, String memberDepart, String memberCard, String id,
                          String memberTel, String memberAddTime, String memberEmail) {
        this.memberName = memberName;
        this.memberDepart = memberDepart;
        this.memberCard = memberCard;
        this.id = id;
        this.memberTel = memberTel;
        this.memberAddTime = memberAddTime;
        this.memberEmail = memberEmail;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberDepart() {
        return memberDepart;
    }

    public void setMemberDepart(String memberDepart) {
        this.memberDepart = memberDepart;
    }

    public String getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(String memberCard) {
        this.memberCard = memberCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public String getMemberAddTime() {
        return memberAddTime;
    }

    public void setMemberAddTime(String memberAddTime) {
        this.memberAddTime = memberAddTime;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }
}
