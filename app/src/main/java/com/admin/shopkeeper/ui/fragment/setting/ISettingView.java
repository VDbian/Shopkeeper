package com.admin.shopkeeper.ui.fragment.setting;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.BossUserInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public interface ISettingView extends IBaseView {
    void showError(String message);

    void showFailed(String failed);

    void getInfoSuccess(List<BossUserInfo> bossUserInfos);
}
