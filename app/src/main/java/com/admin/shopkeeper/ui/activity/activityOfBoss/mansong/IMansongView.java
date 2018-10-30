package com.admin.shopkeeper.ui.activity.activityOfBoss.mansong;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.FreeBean;
import com.admin.shopkeeper.entity.MansongBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IMansongView extends IBaseView {


    void error(String msg);

    void success(List<MansongBean> data);

    void success(String msg);
}
