package com.admin.shopkeeper.ui.fragment.home;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.BussinessBean;
import com.admin.shopkeeper.entity.FoodBussinessBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public interface IHomeView extends IBaseView {
    void error(String msg);

    void success(List<BussinessBean> bussinessBeen);

    void successOfHotFood(List<FoodBussinessBean> data);

    void successOfCoolFood(List<FoodBussinessBean> data);
}
