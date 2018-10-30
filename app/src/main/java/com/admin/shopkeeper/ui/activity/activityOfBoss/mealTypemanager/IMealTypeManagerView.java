package com.admin.shopkeeper.ui.activity.activityOfBoss.mealTypemanager;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MealTypeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IMealTypeManagerView extends IBaseView {

    void error(String msg);

    void success(String msg);


    void success(List<MealTypeBean> foods);
}
