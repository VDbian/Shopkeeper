package com.admin.shopkeeper.entity;

/**
 * Created by Administrator on 2017/7/9 0009.
 * 支付方式实体
 */

public class PayMeEntity {
    private String name;
    private boolean selected;
    private double money;
    private int guid;

    public PayMeEntity(String name, boolean selected, double money) {
        this.name = name;
        this.selected = selected;
        this.money = money;
    }

    public PayMeEntity(String name, boolean selected, double money, int guid) {
        this.name = name;
        this.selected = selected;
        this.money = money;
        this.guid = guid;
    }

    public PayMeEntity(String name, boolean selected, int guid) {
        this.name = name;
        this.selected = selected;
        this.guid = guid;

    }

    public PayMeEntity(String name, boolean selected) {
        this.name = name;
        this.selected = selected;

    }

    public int getGuid() {
        return guid;
    }

    public void setGuid(int guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
