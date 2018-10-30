package com.admin.shopkeeper.ui.activity.activityOfBoss.guazhang;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.admin.shopkeeper.entity.HouseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IGuaZhangView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<GuaZhangBean> datas);
}
