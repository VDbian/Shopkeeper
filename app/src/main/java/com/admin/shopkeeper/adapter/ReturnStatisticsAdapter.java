package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.GiftStatisticsBean;
import com.admin.shopkeeper.entity.ReturnStatisticsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class ReturnStatisticsAdapter extends BaseQuickAdapter<ReturnStatisticsBean, BaseViewHolder> {
    public ReturnStatisticsAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReturnStatisticsBean item) {
        helper.setText(R.id.item_shop, App.INSTANCE().getShopName());
        helper.setText(R.id.item_name, item.getProductName());
        helper.setText(R.id.item_count, String.valueOf(item.getCounts()));
        helper.setText(R.id.item_money, "￥" + String.valueOf(item.getPrice()));
        helper.setText(R.id.item_order, String.valueOf(item.getPcounts()));
    }
}
