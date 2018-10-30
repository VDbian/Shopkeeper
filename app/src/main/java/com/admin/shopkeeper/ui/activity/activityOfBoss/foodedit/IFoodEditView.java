package com.admin.shopkeeper.ui.activity.activityOfBoss.foodedit;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.PrintBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IFoodEditView extends IBaseView {

    void error(String msg);

    void success(String msg);

    void getPrintSuccess(List<PrintBean> datas);

    void getFoodSuccess(List<MenuTypeEntity> menuTypeEntities);

    void imagesuccess(String imagename);
}
