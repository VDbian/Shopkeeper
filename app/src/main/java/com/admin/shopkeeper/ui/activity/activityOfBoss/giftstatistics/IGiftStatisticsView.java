package com.admin.shopkeeper.ui.activity.activityOfBoss.giftstatistics;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.GiftStatisticsBean;
import com.admin.shopkeeper.entity.SaleStatisticsBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IGiftStatisticsView extends IBaseView {


    void error(String msg);

    void success(List<GiftStatisticsBean> list);
}
