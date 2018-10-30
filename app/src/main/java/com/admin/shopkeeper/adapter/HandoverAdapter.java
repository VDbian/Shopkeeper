package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.HandoverBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class HandoverAdapter extends BaseQuickAdapter<HandoverBean, BaseViewHolder> {
    public HandoverAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HandoverBean item) {
        helper.setText(R.id.item_shop, App.INSTANCE().getShopName());
        helper.setText(R.id.item_time, item.getbTime() + "\n" + item.getTime());
        helper.setText(R.id.item_user, String.valueOf(item.getUsername()));
        helper.setText(R.id.item_totle, "￥" + String.valueOf(item.getPrice()));
        helper.setText(R.id.item_stand, "￥" + String.valueOf(item.getPrice()));
        helper.setText(R.id.item_money, "￥" + String.valueOf(item.getPrice()));
    }
}
