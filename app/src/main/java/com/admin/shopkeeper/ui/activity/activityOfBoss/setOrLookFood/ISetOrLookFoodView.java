package com.admin.shopkeeper.ui.activity.activityOfBoss.setOrLookFood;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.FindFoodCouponDownBean;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface ISetOrLookFoodView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<FindFoodCouponDownBean> datas);

    void getFoodTypeSuccess(List<MenuTypeEntity> foodTypes);

    void getFoodSuccess(List<FoodBean> foods);
}
