package com.admin.shopkeeper.ui.activity.info.waimai;

import com.admin.shopkeeper.base.IBaseView;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public interface IWaiMaiView extends IBaseView {
    void warning(String s);

    void error(String string);

    void kuaisuSuccess(String result);
}
