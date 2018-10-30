package com.admin.shopkeeper.entity;

import android.support.annotation.DrawableRes;

/**
 * Created by admin on 2017/4/7.
 * //        labels.add(new Label("换桌", R.mipmap.icon_desk));
 * //        labels.add(new Label("撤单", R.mipmap.icon_undo));
 * //        labels.add(new Label("并单", R.mipmap.icon_merge));
 * //        labels.add(new Label("后厨打印", R.mipmap.icon_print_after));
 * //        labels.add(new Label("前台打印", R.mipmap.icon_print_before));
 * //        labels.add(new Label("整单催菜", R.mipmap.icon_hurry));
 * //        labels.add(new Label("修改人数", R.mipmap.iocn_person_number));
 * //        labels.add(new Label("绑定会员", R.mipmap.icon_vip));
 * //        labels.add(new Label("账单结算", R.mipmap.icon_bil));
 * labels.add(new Label("加菜", R.mipmap.icon_addfood));
 */

public class Label {
    public static final int desk = 1;
    public static final int undo = 2;
    public static final int merge = 3;
    public static final int print_after = 4;
    public static final int hurry = 5;
    public static final int person_number = 6;
    public static final int vip = 7;
    public static final int bil = 8;
    public static final int addfood = 9;
    public static final int before = 10;
    public static final int rebill = 15;
    public static final int consumption = 16;
    //    “取消”“确认”“已完成”
    public static final int cancel = 11;
    public static final int finish = 12;
    public static final int confirm = 13;
    public static final int bind_table = 14;

    private int type = 0;
    private String labelName;
    private int labelID;


    public Label(String labelName, @DrawableRes int labelID) {

        this.labelName = labelName;
        this.labelID = labelID;
    }

    public Label(String labelName, @DrawableRes int labelID, int type) {

        this.labelName = labelName;
        this.labelID = labelID;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getLabelID() {
        return labelID;
    }

    public void setLabelID(@DrawableRes int labelID) {
        this.labelID = labelID;
    }
}
