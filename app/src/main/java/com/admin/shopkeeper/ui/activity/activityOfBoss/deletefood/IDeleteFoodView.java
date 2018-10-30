package com.admin.shopkeeper.ui.activity.activityOfBoss.deletefood;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.FoodBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IDeleteFoodView extends IBaseView {

    void error(String msg);

    void success(String msg);

    void success(List<FoodBean> foods);

}
