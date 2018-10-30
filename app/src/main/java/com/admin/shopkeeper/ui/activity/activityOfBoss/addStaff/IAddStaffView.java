package com.admin.shopkeeper.ui.activity.activityOfBoss.addStaff;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.DepartmentBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IAddStaffView extends IBaseView {

    void error(String msg);

    void success(String msg);

    void success(List<DepartmentBean> departmentBeen);

}
