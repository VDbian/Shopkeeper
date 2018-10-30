package com.admin.shopkeeper.ui.activity.activityOfBoss.desk;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.DeskBussinessBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IDeskView extends IBaseView {


    void error(String msg);

    void success(List<DeskBussinessBean> data);
}
