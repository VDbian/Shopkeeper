package com.admin.shopkeeper.ui.activity.activityOfBoss.house;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.HouseBean;
import com.admin.shopkeeper.entity.PrintBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IHouseView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<HouseBean> datas);
}
