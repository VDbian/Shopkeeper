package com.admin.shopkeeper.ui.activity.activityOfBoss.shopPermissionEdit;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.PermissionsOfUser;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IShopPermissionEditView extends IBaseView {
    void error(String msg);

    void success(List<PermissionsOfUser> list);

    void success(String msg);
}
