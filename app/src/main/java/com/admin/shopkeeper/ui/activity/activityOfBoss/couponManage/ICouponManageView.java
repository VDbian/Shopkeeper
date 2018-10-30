package com.admin.shopkeeper.ui.activity.activityOfBoss.couponManage;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.MemberLevelManageBean;
import com.admin.shopkeeper.entity.ShopBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface ICouponManageView extends IBaseView {
    void error(String msg);

    void success(String msg);

    void success(List<CouponManageBean> couponManageBeanList);

    void shopsuccess(CouponManageBean bean, List<ShopBean> shopBeen);
}
