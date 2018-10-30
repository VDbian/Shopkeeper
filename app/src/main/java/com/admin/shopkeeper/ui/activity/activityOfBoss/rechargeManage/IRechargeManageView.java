package com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeManage;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MemberLevelManageBean;
import com.admin.shopkeeper.entity.RechargeManageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface IRechargeManageView extends IBaseView {
    void error(String msg);

    void success(String msg);

    void success(List<RechargeManageBean> rechargeManageBeanList);
}
