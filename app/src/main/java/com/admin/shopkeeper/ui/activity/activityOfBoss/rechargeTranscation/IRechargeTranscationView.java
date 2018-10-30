package com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeTranscation;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MemberTranscationBean;
import com.admin.shopkeeper.entity.RechargeDetailTableBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface IRechargeTranscationView extends IBaseView {
    void error(String msg);

    void success(String msg);

    void success(List<MemberTranscationBean> memberTranscationBeanList);

}
