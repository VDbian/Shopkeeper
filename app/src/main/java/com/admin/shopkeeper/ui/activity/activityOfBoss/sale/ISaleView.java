package com.admin.shopkeeper.ui.activity.activityOfBoss.sale;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.entity.SaleBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface ISaleView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<SaleBean> datas);
}
