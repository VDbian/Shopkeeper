package com.admin.shopkeeper.ui.activity.queue;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.DeskTypeBean;
import com.admin.shopkeeper.entity.QueueBean;
import com.admin.shopkeeper.entity.WeightBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IQueueView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<QueueBean> datas);

    void tableTypeSuccess(List<DeskTypeBean> deskTypeBeen);
}
