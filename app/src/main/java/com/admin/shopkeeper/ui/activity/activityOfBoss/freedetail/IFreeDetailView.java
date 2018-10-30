package com.admin.shopkeeper.ui.activity.activityOfBoss.freedetail;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.FreeDetailBean;
import com.admin.shopkeeper.entity.HandoverDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IFreeDetailView extends IBaseView {

    void error(String msg);

    void success(List<FreeDetailBean> list);
}
