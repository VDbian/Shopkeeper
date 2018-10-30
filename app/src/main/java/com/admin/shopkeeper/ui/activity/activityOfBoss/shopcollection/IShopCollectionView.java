package com.admin.shopkeeper.ui.activity.activityOfBoss.shopcollection;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.ReturnBussinessBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public interface IShopCollectionView extends IBaseView {


    void error(String msg);

    void success(List<ShopCollectionBean> data);
}
