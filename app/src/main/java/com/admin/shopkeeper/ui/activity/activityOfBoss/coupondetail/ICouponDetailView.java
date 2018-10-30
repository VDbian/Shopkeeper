package com.admin.shopkeeper.ui.activity.activityOfBoss.coupondetail;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.GuizeBean;
import com.admin.shopkeeper.entity.ShopBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface ICouponDetailView extends IBaseView {
    void error(String msg);

    void success(String msg);

    void deleteSuccess(String msg);

    void shopsuccess(CouponManageBean bean, List<ShopBean> shopBeen);

    void shopdetail(List<ShopBean> shopBeen);

    void showDetail(List<GuizeBean> list);
}
