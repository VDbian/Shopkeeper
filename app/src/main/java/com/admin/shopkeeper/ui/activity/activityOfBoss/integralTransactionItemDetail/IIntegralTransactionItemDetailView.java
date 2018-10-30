package com.admin.shopkeeper.ui.activity.activityOfBoss.integralTransactionItemDetail;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.IntegralTranscationItemDetailBean;
import com.admin.shopkeeper.entity.RechargeTranscationItemBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface IIntegralTransactionItemDetailView extends IBaseView {
    void error(String msg);

    void success(List<IntegralTranscationItemDetailBean> datas);

}
