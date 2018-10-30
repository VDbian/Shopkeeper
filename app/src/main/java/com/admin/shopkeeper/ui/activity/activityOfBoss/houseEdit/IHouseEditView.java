package com.admin.shopkeeper.ui.activity.activityOfBoss.houseEdit;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.HouseBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.RoomTypeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IHouseEditView extends IBaseView {

    void error(String msg);

    void success(String msg);

    void success(List<RoomTypeBean> data);
}
