package com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeTransactionItemDetail;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.CollectionBean;
import com.admin.shopkeeper.entity.RechargeTranscationItemBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface IRechargeTranscationItemDetailView extends IBaseView {
    void error(String msg);

    void success(List<RechargeTranscationItemBean> datas);

}
