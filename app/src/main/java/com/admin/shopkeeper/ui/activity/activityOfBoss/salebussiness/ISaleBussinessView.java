package com.admin.shopkeeper.ui.activity.activityOfBoss.salebussiness;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.FoodBussinessBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface ISaleBussinessView extends IBaseView {


    void error(String msg);

    void success(List<FoodBussinessBean> data);
}
