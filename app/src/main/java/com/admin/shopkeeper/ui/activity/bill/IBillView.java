package com.admin.shopkeeper.ui.activity.bill;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.CardBean;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.entity.DaZheEntity;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.admin.shopkeeper.entity.MemberBean;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.admin.shopkeeper.entity.WeixinOrderBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IBillView extends IBaseView {
    void warning(String s);

    void error(String string);

    void billSuccess(String s, String result);

    void canJuFei(double i);

    void dazheSucccess(double aDouble);

    void otherYouhuiSucccess(double aDouble);

    void weixinSuccess();

    void scanBillSuccess(String payType,String result,double money , String memberId ,String str);

    void getImageSuccess(int p, String result, PayMeEntity e);

    void showDazhe(List<DaZheEntity> daZheEntityList);

    void showDetail(Order order, List<OrderDetailFood> orderDetailFoods);

    void guazhangSuccess(List<GuaZhangBean> result, PayMeEntity entity);

    void saleSuccess(MemberBean member, List<CardBean> cardBeens);

    void success(List<WeixinOrderBean> weixinOrderBeen);

    void fail();

    void success(String msg);

    void cancelSuccess();

    void successOfGetCouponLine(List<CouponLineDownBean> couponLineDownBeans);
}
