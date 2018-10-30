package com.admin.shopkeeper.ui.activity.activityOfBoss.jiondetail;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.GuazhangDetailBean;
import com.admin.shopkeeper.entity.HandoverDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IJionDetailView extends IBaseView {

    void error(String msg);

    void success(String msg);

    void success(List<HandoverDetailBean> datas);
}
