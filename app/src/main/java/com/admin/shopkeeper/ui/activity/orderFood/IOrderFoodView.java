package com.admin.shopkeeper.ui.activity.orderFood;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.OrderfoodEntity;

import java.util.List;

/**
 * Created by admin on 2017/6/30.
 */

public interface IOrderFoodView extends IBaseView {
    void warning(String s);
    //void showFoodEditDialog(FoodEntity entity, OrderfoodEntity orderfoodEntity);

    void success(List<MenuTypeEntity> menuTypeEntities);

    void error(String string);

    void orderSuccess(String s);

    void kuaisuSuccess(String result, double monery,boolean isquick , boolean isScan , boolean isEditTabName);

    void querySuccess(List<FoodEntity> foodEntities);

    void billSuccess(String msg,String result);

    void bill(String payType,String result,double money,String memberId ,String str);
}
