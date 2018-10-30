package com.admin.shopkeeper.ui.activity.activityOfBoss.desktypeedit;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.PersonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IDeskTypeEditView extends IBaseView {

    void error(String msg);

    void success(String msg);

    void personSuccess(List<PersonBean> data);
}
