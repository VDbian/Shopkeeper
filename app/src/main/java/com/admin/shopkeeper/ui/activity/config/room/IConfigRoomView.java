package com.admin.shopkeeper.ui.activity.config.room;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.RoomEntity;

import java.util.List;

/**
 * Created by guxiaogasumi on 2017/6/17.
 */

public interface IConfigRoomView  extends IBaseView{
    void error(String message);

    void showRooms(List<RoomEntity> roomEntities);

    void warning(String message);

    void noDataWithDB();

}
