package com.admin.shopkeeper.ui.activity.activityOfBoss.sensitiveOpearation;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.admin.shopkeeper.entity.SensitiveOpearation;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface ISensitiveOpearationView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<SensitiveOpearation> list);
}
