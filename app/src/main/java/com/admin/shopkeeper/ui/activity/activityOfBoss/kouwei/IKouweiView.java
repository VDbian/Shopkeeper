package com.admin.shopkeeper.ui.activity.activityOfBoss.kouwei;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.HouseBean;
import com.admin.shopkeeper.entity.KouweiBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IKouweiView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<KouweiBean> datas);
}
