package com.admin.shopkeeper.ui.activity.activityOfBoss.jion;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.HandoverBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IJionView extends IBaseView {


    void error(String msg);

    void success(List<HandoverBean> data);

}
