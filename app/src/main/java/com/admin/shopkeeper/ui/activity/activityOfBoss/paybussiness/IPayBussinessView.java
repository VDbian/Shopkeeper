package com.admin.shopkeeper.ui.activity.activityOfBoss.paybussiness;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.PayBussinessBean;
import com.admin.shopkeeper.entity.WaiterBussinessBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IPayBussinessView extends IBaseView {


    void error(String msg);

    void success(List<PayBussinessBean> data);
}
