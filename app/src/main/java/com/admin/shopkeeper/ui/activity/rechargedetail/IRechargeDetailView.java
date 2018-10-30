package com.admin.shopkeeper.ui.activity.rechargedetail;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MemberBean;
import com.admin.shopkeeper.entity.RechargeBean;
import com.admin.shopkeeper.entity.RechargeItemBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IRechargeDetailView extends IBaseView {

    void error(String msg);

    void success(String msg);

    void saleSuccess(MemberBean memberBean);

    void productSuccess(List<RechargeItemBean> rechargeItemBeen);

    void checkSuccess(int type , RechargeBean bean);
}
