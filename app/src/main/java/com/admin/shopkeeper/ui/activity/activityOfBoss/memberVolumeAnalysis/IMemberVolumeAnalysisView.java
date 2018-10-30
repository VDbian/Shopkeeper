package com.admin.shopkeeper.ui.activity.activityOfBoss.memberVolumeAnalysis;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MemberVolumeAnalysisBean;
import com.admin.shopkeeper.entity.RechargeDetailTableBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface IMemberVolumeAnalysisView extends IBaseView {
    void error(String msg);

    void success(String msg);

    void success(List<MemberVolumeAnalysisBean> memberVolumeAnalysisBeanList);

}
