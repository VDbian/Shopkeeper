package com.admin.shopkeeper.ui.activity.activityOfBoss.mealEdit;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MealTypeBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IMealEditView extends IBaseView {

    void error(String msg);

    void success(String msg);

    void successOfGetType(List<MealTypeBean> menuTypeEntities);

    void imagesuccess(String imagename);
}
