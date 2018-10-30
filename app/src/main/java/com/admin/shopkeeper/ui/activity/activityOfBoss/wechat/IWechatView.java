package com.admin.shopkeeper.ui.activity.activityOfBoss.wechat;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.WechatBean;
import com.admin.shopkeeper.entity.WeightBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IWechatView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(WechatBean bean);

}
