package com.admin.shopkeeper.ui.activity.activityOfBoss.returncause;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.RetCauseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IReturnCauseView extends IBaseView {


    void error(String msg);

    void success(List<RetCauseBean> retCauseBeen);

    void success(String msg);
}
