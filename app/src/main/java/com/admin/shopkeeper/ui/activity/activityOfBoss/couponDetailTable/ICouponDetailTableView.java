package com.admin.shopkeeper.ui.activity.activityOfBoss.couponDetailTable;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.CouponDetailTableBean;
import com.admin.shopkeeper.entity.MemberConsumeDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface ICouponDetailTableView extends IBaseView {
    void error(String msg);

    void success(String msg);

    void success(List<CouponDetailTableBean> couponDetailTableBeanList);

}
