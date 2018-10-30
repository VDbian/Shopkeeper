package com.admin.shopkeeper.ui.activity.activityOfBoss.memberconsumeDetails;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.MemberConsumeDetailBean;
import com.admin.shopkeeper.entity.MemberTranscationBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface IMemberConsumeDetailView extends IBaseView {
    void error(String msg);

    void success(String msg);

    void success(List<MemberConsumeDetailBean> memberConsumeDetailBeanList);

}
