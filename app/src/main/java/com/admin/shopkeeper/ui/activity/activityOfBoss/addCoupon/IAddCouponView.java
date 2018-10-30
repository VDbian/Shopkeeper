package com.admin.shopkeeper.ui.activity.activityOfBoss.addCoupon;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.ProductBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IAddCouponView extends IBaseView {

    void error(String msg);

    void success(String msg);

    void productsuccess(List<ProductBean> productBeen);
}
