package com.admin.shopkeeper.ui.activity.activityOfBoss.desktype;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.DeskTypeBean;
import com.admin.shopkeeper.entity.HouseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IDeskTypeView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<DeskTypeBean> datas);
}
