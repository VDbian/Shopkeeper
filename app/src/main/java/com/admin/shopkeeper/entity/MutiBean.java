package com.admin.shopkeeper.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/18.
 */

public class MutiBean implements Serializable {
    String text;
    boolean select;
    int value;

    public MutiBean() {
    }

    public MutiBean(String text, boolean select, int value) {
        this.text = text;
        this.select = select;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
