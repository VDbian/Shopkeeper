package com.admin.shopkeeper.ui.activity.activityOfBoss.bindfood;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.FindFoodCouponDownBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IBindFoodView extends IBaseView {


    void error(String msg);

    void success(List<FindFoodCouponDownBean> list);
}
