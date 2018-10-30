package com.admin.shopkeeper.ui.activity.foodsList;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MenuTypeEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public interface IFoodsListView extends IBaseView {
    void error(String message);

    void warning(String s);

    void success(List<MenuTypeEntity> menuTypeEntities);

    void getService();

}
