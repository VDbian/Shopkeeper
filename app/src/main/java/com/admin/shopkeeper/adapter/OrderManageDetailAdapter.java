package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.OrderManageDetail;
import com.admin.shopkeeper.entity.RechargeDetailTableBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class OrderManageDetailAdapter extends BaseQuickAdapter<OrderManageDetail, BaseViewHolder> {
    public OrderManageDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderManageDetail item) {
        helper.setText(R.id.item_food_name, item.getProductName());
        helper.setText(R.id.item_count, item.getCount());
        helper.setText(R.id.item_money, String.valueOf(item.getTotalPrice()));
        helper.setText(R.id.item_username,item.getUserNmae());
    }
}
