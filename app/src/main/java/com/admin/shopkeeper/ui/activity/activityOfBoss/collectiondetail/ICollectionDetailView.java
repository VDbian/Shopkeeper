package com.admin.shopkeeper.ui.activity.activityOfBoss.collectiondetail;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.CollectionBean;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.ShopBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0009.
 */

public interface ICollectionDetailView extends IBaseView {
    void error(String msg);

    void success(List<CollectionBean> datas);

}
