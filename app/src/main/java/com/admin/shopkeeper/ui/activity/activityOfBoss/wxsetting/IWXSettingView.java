package com.admin.shopkeeper.ui.activity.activityOfBoss.wxsetting;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.WechatBean;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IWXSettingView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(WechatBean bean);

}
