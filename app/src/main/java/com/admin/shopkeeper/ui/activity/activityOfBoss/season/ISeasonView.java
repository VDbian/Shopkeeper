package com.admin.shopkeeper.ui.activity.activityOfBoss.season;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.KouweiBean;
import com.admin.shopkeeper.entity.SeasonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface ISeasonView extends IBaseView {


    void error(String msg);

    void success(String msg);

    void success(List<SeasonBean> datas);
}
