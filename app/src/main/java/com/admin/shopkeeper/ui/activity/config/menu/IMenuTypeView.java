package com.admin.shopkeeper.ui.activity.config.menu;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MenuTypeEntity;

import java.util.List;

/**
 * Created by guxiaogasumi on 2017/6/18.
 */

public interface IMenuTypeView extends IBaseView {
    void error(String message);

    void warning(String s);

    void showMenus(List<MenuTypeEntity> entities);

    void noDataWithDB();
}
