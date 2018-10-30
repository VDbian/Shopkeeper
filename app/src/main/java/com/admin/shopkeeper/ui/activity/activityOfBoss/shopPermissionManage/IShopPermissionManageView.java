package com.admin.shopkeeper.ui.activity.activityOfBoss.shopPermissionManage;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.ShopPermissionManageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IShopPermissionManageView extends IBaseView {
    void error(String msg);

    void success(List<ShopPermissionManageBean> shopPermissionManageBeen);

    void success(String msg);
}
