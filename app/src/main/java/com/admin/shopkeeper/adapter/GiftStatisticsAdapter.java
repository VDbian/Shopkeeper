package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.GiftStatisticsBean;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class GiftStatisticsAdapter extends BaseQuickAdapter<GiftStatisticsBean, BaseViewHolder> {
    public GiftStatisticsAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftStatisticsBean item) {
        helper.setText(R.id.item_shop, App.INSTANCE().getShopName());
        helper.setText(R.id.item_name, item.getProductName());
        helper.setText(R.id.item_count, String.valueOf(item.getGiving()));
        helper.setText(R.id.item_money, "￥" + String.valueOf(item.getPrice()));
    }
}
