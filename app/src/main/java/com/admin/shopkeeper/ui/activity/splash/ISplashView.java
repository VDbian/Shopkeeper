package com.admin.shopkeeper.ui.activity.splash;

import com.admin.shopkeeper.base.IBaseView;

/**
 * Created by zeng on 2017/3/28.
 */

public interface ISplashView  extends IBaseView{

    void update(int code);

    void error(String msg);
}
