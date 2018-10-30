package com.admin.shopkeeper.entity;

/**
 * Created by zeng on 2017/4/23.
 */

public class MenuList {
    private String id;
    private String name;
    private int number;
    private double price;
    private double vipPrice;

    public MenuList(String id, String name, int number, double price, double vipPrice) {

        this.id = id;
        this.name = name;
        this.number = number;
        this.price = price;
        this.vipPrice = vipPrice;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }
}
