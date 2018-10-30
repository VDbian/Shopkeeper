package com.admin.shopkeeper.ui.activity.activityOfBoss.sensitiveOpearationDetail;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.OrderManageDetail;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface ISensitiveDetailView extends IBaseView {
    void error(String msg);

    void success(String msg);

    void success(List<OrderManageDetail> orderManageDetailList);

}
