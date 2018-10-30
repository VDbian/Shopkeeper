package com.admin.shopkeeper.ui.activity.activityOfBoss.deskmanager;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.DeskBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IDeskManagerView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<DeskBean> datas);
}
