package com.admin.shopkeeper.entity;

/**
 * Created by Administrator on 2018/4/27.
 */

public class TimeTypeBean {
    String name;
    boolean select;

    public TimeTypeBean() {
    }

    public TimeTypeBean(String name, boolean select) {
        this.name = name;
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
