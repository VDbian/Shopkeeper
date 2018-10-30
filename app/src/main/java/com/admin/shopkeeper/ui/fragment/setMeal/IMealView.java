package com.admin.shopkeeper.ui.fragment.setMeal;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MenuTypeEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public interface IMealView extends IBaseView {
    void error(String message);

    void warning(String s);

    void success(List<MenuTypeEntity> menuTypeEntities);

    void getService();
}
