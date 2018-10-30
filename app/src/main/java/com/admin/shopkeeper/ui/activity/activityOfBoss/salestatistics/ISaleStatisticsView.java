package com.admin.shopkeeper.ui.activity.activityOfBoss.salestatistics;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface ISaleStatisticsView extends IBaseView {


    void error(String msg);

    void foodSuccess(List<FoodEntity> foodEntities);

    void success(List<SaleStatisticsBean> list);

    void totle(List<SaleStatisticsBean> saleStatisticsBeen);
}
