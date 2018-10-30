package com.admin.shopkeeper.ui.activity.activityOfBoss.saleStatisticsProduct;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.admin.shopkeeper.entity.SaleStatisticsProductBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface ISaleStatisticsProductView extends IBaseView {


    void error(String msg);

    void foodSuccess(List<FoodEntity> foodEntities);

    void success(List<SaleStatisticsProductBean> list);
}
