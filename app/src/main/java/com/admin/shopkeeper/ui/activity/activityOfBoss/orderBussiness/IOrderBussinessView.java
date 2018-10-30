package com.admin.shopkeeper.ui.activity.activityOfBoss.orderBussiness;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.FoodBussinessBean;
import com.admin.shopkeeper.entity.OrderBussinessBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IOrderBussinessView extends IBaseView {


    void error(String msg);

    void success(List<OrderBussinessBean> data);
}
