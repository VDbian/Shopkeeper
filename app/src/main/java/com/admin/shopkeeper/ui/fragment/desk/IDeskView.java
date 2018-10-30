package com.admin.shopkeeper.ui.fragment.desk;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.entity.User;

import java.util.List;

/**
 * Created by admin on 2017/3/29.
 */

public interface
IDeskView extends IBaseView {
    void showDB(List<RoomEntity> roomEntities);

}
