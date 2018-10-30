package com.admin.shopkeeper.ui.activity.activityOfBoss.staffManager;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.ShopPermissionManageBean;
import com.admin.shopkeeper.entity.StaffManageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface IStaffManageView extends IBaseView {
    void error(String msg);

    void success(String msg);

    void success(List<StaffManageBean> staffManageBeen);
}
