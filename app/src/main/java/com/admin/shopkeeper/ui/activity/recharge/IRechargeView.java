package com.admin.shopkeeper.ui.activity.recharge;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MemberBean;
import com.admin.shopkeeper.entity.RechargeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IRechargeView extends IBaseView {
    void error(String msg);

    void success(List<RechargeBean> list);

    void checkSuccess(int type,RechargeBean bean);

    void searchSuccess(List<RechargeBean> rechargeBeen);
}
