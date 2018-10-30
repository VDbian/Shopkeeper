package com.admin.shopkeeper.ui.activity.activityOfBoss.print;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.PrintBean;
import com.admin.shopkeeper.entity.RetCauseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IPrintManagerView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<PrintBean> datas);
}
