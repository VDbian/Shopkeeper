package com.admin.shopkeeper.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class SaleStatisticsAdapter extends BaseQuickAdapter<SaleStatisticsBean, BaseViewHolder> {
    public SaleStatisticsAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SaleStatisticsBean item) {

            helper.setText(R.id.item_shop, App.INSTANCE().getShopName());
            helper.setText(R.id.item_type, item.getProductTypeName());
            helper.setText(R.id.item_id, item.getId());
            helper.setText(R.id.item_name, item.getProductName());
            helper.setText(R.id.item_count, String.valueOf(item.getCounts()));
            helper.setText(R.id.item_money, "￥" + String.valueOf(item.getTotalPrice()));
            helper.setText(R.id.item_free, "￥" + String.valueOf(item.getFreePrice()));
            helper.setText(R.id.item_free_l, "￥" + String.valueOf(item.getChargeMoney()));

    }
}
