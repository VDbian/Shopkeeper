package com.admin.shopkeeper.ui.activity.activityOfBoss.couponLineDown;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.entity.MansongBean;
import com.admin.shopkeeper.entity.ShopBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface ICouponLineDownView extends IBaseView {


    void error(String msg);

    void success(List<CouponLineDownBean> data);

    void success(String msg);

//    void shopsuccess(CouponLineDownBean bean, List<ShopBean> data);
}
