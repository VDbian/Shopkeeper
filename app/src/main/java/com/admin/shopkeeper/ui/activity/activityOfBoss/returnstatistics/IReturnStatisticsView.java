package com.admin.shopkeeper.ui.activity.activityOfBoss.returnstatistics;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.ReturnStatisticsBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IReturnStatisticsView extends IBaseView {


    void error(String msg);

    void success(List<ReturnStatisticsBean> data);
}
