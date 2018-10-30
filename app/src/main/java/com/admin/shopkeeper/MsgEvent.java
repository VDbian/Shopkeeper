package com.admin.shopkeeper;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class MsgEvent {

    public static final int ChangeTable = 1;//换桌
    public static final int merge = 2;//合并
    public static final int Turn_the_dish = 3;//转菜
    public static final int TangDian = 4;//堂点
    public static final int AddFood = 5;//加菜
    public static final int OrderList = 6;//订单列表
    public static final int OrderTable = 9;//订单桌台
    public static final int ChangeSuccess = 7;//换桌成功
    public static final int bill = 8;
    public static final int kuaican = 10;
    public static final int kuaicanSuccess = 11;
    public static final int waimaiSuccess = 12;
    public static final int yudingSuccess = 13;
    public static final int bindTable = 14;
    public static final int bindTableSuccess = 15;
    //    public static final int undoSuccess = 16;
    public static final int billSuccess = 17;
    public static final int message = 18;
    public static final int OrderTableChangeSuccess = 19;

    public static final int mergeSuccess = 20;
    public static final int mergeSuccessFinish = 21;
    public static final int TurnSuccess = 22;

    public static final int reBillSuccess = 23;

    public static final int oneSale = 24;//单个菜品打折

    public MsgEvent(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public MsgEvent(int type, Object data, Object other) {
        this.type = type;
        this.data = data;
        this.other = other;
    }

    public MsgEvent(int type) {
        this.type = type;
    }

    private int type;
    private Object data;
    private Object other;

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
