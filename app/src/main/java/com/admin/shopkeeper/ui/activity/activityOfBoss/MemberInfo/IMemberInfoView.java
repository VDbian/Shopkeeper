package com.admin.shopkeeper.ui.activity.activityOfBoss.MemberInfo;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MemberInfoOfItemBean;
import com.admin.shopkeeper.entity.OrderBussinessBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IMemberInfoView extends IBaseView {


    void error(String msg);

    void success(List<MemberInfoOfItemBean> data);

    void success(String msg);
}
