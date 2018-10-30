package com.admin.shopkeeper.ui.activity.activityOfBoss.deskopen;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.DeskOpenBean;
import com.admin.shopkeeper.entity.HandoverBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IDeskopenView extends IBaseView {


    void error(String msg);

    void success(List<DeskOpenBean> data);

}
