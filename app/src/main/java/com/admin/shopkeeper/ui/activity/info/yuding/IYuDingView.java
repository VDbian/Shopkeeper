package com.admin.shopkeeper.ui.activity.info.yuding;

import com.admin.shopkeeper.base.IBaseView;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public interface IYuDingView extends IBaseView{
    void warning(String s);

    void kuaisuSuccess(String result);

    void error(String string);
}
