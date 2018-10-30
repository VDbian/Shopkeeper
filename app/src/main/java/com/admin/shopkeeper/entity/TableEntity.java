package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by guxiaogasumi on 2017/6/18.
 * <p>
 * {
 * "ROOMTABLEID":"ebb5d18a-047f-44d9-98f9-baa1ad2052b1",
 * "TABLENAME":"1号",
 * "RESTAURANTID":"4b176f0e-0553-4094-8181-5048641b20ef",
 * "ROOMID":"3066a3e0-9a85-4a96-bc11-50a9081b5651",
 * "SORTNO":1,
 * "BILLId":"4a120c7f-1aab-4c34-bd7d-d176b4b90f2c",
 * "IsOpen":"1",
 * "PersonNo":"0",
 * "TableWareCount":1,
 * "PERSONCOUNTS":1
 * }
 */

public class TableEntity implements Serializable {

    @SerializedName("ROOMTABLEID")
    private String roomTableID;

    @SerializedName("TABLENAME")
    private String tableName;

    @SerializedName("RESTAURANTID")
    private String restaurantID;

    @SerializedName("ROOMID")
    private String roomID;

    @SerializedName("SORTNO")
    private int sortNO;

    @SerializedName("BILLId")
    private String billID;

    @SerializedName("IsOpen")
    private String open;

    @SerializedName("PersonNo")
    private String personNo;

    @SerializedName("TableWareCount")
    private String tableWareCount;

    @SerializedName("PERSONCOUNTS")
    private int personCounts;

    @SerializedName("Price")
    private double price;

    @SerializedName("KaiTime")
    private String kaiTime;

    public String getKaiTime() {
        return kaiTime;
    }

    public void setKaiTime(String kaiTime) {
        this.kaiTime = kaiTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private boolean selector;

    public boolean isSelector() {
        return selector;
    }

    public void setSelector(boolean selector) {
        this.selector = selector;
    }

    public String getRoomTableID() {
        return roomTableID;
    }

    public void setRoomTableID(String roomTableID) {
        this.roomTableID = roomTableID;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public int getSortNO() {
        return sortNO;
    }

    public void setSortNO(int sortNO) {
        this.sortNO = sortNO;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getTableWareCount() {
        return tableWareCount;
    }

    public void setTableWareCount(String tableWareCount) {
        this.tableWareCount = tableWareCount;
    }

    public int getPersonCounts() {
        return personCounts;
    }

    public void setPersonCounts(int personCounts) {
        this.personCounts = personCounts;
    }

    @Override
    public String toString() {
        return "TableEntity{" +
                "roomTableID='" + roomTableID + '\'' +
                ", tableName='" + tableName + '\'' +
                ", restaurantID='" + restaurantID + '\'' +
                ", roomID='" + roomID + '\'' +
                ", sortNO=" + sortNO +
                ", billID='" + billID + '\'' +
                ", open='" + open + '\'' +
                ", personNo='" + personNo + '\'' +
                ", tableWareCount='" + tableWareCount + '\'' +
                ", personCounts=" + personCounts +
                ", price=" + price +
                ", kaiTime='" + kaiTime + '\'' +
                ", selector=" + selector +
                '}';
    }
}
