package com.admin.shopkeeper.ui.activity.home;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.RoomEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public interface IHomeView extends IBaseView {

    void error(String message);

    void warning(String s);

    void success(List<MenuTypeEntity> menuTypeEntities);

    void showRooms(List<RoomEntity> roomEntities);
}
