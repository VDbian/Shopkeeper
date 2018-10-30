package com.admin.shopkeeper.ui.activity.activityOfBoss.free;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.FreeBean;
import com.admin.shopkeeper.entity.HandoverBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IFreeView extends IBaseView {


    void error(String msg);

    void success(List<FreeBean> data);

}
