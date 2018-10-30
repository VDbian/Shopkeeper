package com.admin.shopkeeper.ui.activity.login;

import com.admin.shopkeeper.base.IBaseView;

/**
 * Created by zeng on 2017/3/28.
 */

public interface ILoginView extends IBaseView {
    void showError(String message);

    void showFailed(String failed);

    void loginSuccess();
}
