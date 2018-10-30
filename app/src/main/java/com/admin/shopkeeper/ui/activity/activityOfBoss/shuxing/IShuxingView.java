package com.admin.shopkeeper.ui.activity.activityOfBoss.shuxing;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.KouweiBean;
import com.admin.shopkeeper.entity.ShuxingBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IShuxingView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<ShuxingBean> datas);
}
