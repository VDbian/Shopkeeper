package com.admin.shopkeeper.ui.activity.activityOfBoss.boss;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.BossUserInfo;
import com.admin.shopkeeper.entity.ChainBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IBossMainView extends IBaseView {

    void getInfoSuccess(List<BossUserInfo> bossUserInfos);

    void getChainInfoSuccess(List<ChainBean> chainBeanList);

    void error(String msg);
}
