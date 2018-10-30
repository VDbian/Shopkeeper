package com.admin.shopkeeper.ui.fragment.order;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.TPayType;

import java.util.List;

/**
 * Created by admin on 2017/3/29.
 */

public interface IOrderView extends IBaseView {
    void warning(String message);

    void error(String message);

    void success(List<Order> orders);


    void toDetail(Order order, List<OrderDetailFood> detailFoods , List<TPayType> tPayTypes, int position);
}
