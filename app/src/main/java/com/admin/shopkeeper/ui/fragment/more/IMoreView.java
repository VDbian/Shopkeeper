package com.admin.shopkeeper.ui.fragment.more;

import com.admin.shopkeeper.base.IBaseView;

/**
 * Created by admin on 2017/3/29.
 */

public interface IMoreView extends IBaseView{

    void warning(String s);

    void success(String s);

    void error(String string);

    void toLogin();
}
