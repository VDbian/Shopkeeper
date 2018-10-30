package com.admin.shopkeeper.ui.activity.activityOfBoss.deskedit;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.HouseBean;
import com.admin.shopkeeper.entity.PersonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IDeskEditView extends IBaseView {

    void error(String msg);

    void success(String msg);

    void success(List<HouseBean> datas);

    void personSuccess(List<PersonBean> datas);
}
