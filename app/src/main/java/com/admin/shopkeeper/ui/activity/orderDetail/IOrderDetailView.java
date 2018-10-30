package com.admin.shopkeeper.ui.activity.orderDetail;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.RetreatReason;
import com.admin.shopkeeper.entity.TPayType;

import java.util.List;

/**
 * Created by zeng on 2017/4/23.
 */

public interface IOrderDetailView extends IBaseView {
    void warning(String s);

    void error(String s);

    void message(String s);

    void toDetail(List<OrderDetailFood> orderDetailFoods , List<TPayType> tPayTypes);

    void undoSuccess();

    void peopleNumSuccess(int peopleNum, int canJuNum);

    void retreatFoodSuccess(OrderDetailFood p);


    void cuicaiSuccess();

    void cancelSuccess();

    void confirmSuccess();

    void finishSuccess();

    void showReason(List<RetreatReason> retreatReasons, OrderDetailFood orderDetailFood);

    void givingSuccess();

    void orderSuccess(Order order, List<OrderDetailFood> orderDetailFoods);
}
