package com.admin.shopkeeper.ui.fragment.message;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.TPayType;

import java.util.List;

/**
 * Created by admin on 2017/3/29.
 */

public interface IMessageView extends IBaseView {
    void success(List<Order> test);

    void warning(String message);

    void error(String message);

    void nodata();

    void toDetail(Order item, List<OrderDetailFood> orderDetailFoods, List<TPayType> tPayTypes , int position);
}
