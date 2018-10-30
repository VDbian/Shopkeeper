package com.admin.shopkeeper.ui.activity.activityOfBoss.memberManager;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MemberInfoBean;
import com.admin.shopkeeper.entity.ShopPermissionManageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface IMemberManageView extends IBaseView {
    void error(String msg);

    void success(String msg);

    void success(List<MemberInfoBean> memberInfoBeanList);

    void searchsuccess(List<MemberInfoBean> memberInfoBeanList);
}
