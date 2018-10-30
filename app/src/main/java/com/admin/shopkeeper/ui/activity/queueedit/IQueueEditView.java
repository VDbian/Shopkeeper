package com.admin.shopkeeper.ui.activity.queueedit;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.DeskBean;
import com.admin.shopkeeper.entity.DeskTypeBean;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.entity.TableEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IQueueEditView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void tableTypeSuccess(List<DeskTypeBean> deskTypeBeen);

    void tableSuccess(List<TableEntity> deskBeen);

    void showDB(List<RoomEntity> roomEntities);
}
