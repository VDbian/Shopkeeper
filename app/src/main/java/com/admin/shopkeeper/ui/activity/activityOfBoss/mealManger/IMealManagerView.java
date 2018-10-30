package com.admin.shopkeeper.ui.activity.activityOfBoss.mealManger;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MealBean;
import com.admin.shopkeeper.entity.MealTypeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IMealManagerView extends IBaseView {

    void error(String msg);

    void success(String msg);

    void success(List<MealBean> foods);

    void successOfGetType(List<MealTypeBean> foods);
}
