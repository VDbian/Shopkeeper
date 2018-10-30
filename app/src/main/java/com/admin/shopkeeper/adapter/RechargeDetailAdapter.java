package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.RechargeDetailTableBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class RechargeDetailAdapter extends BaseQuickAdapter<RechargeDetailTableBean, BaseViewHolder> {
    public RechargeDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeDetailTableBean item) {
        helper.setText(R.id.item_name, App.INSTANCE().getShopName());
        helper.setText(R.id.item_date, item.getDate());
        helper.setText(R.id.item_sale, String.valueOf(item.getWeixin()));
        helper.setText(R.id.item_charge, String.valueOf(item.getDiannei()));
        helper.setText(R.id.item_free, String.valueOf(item.getTotalPrice()));
        helper.setText(R.id.item_real, String.valueOf(item.getZengsong()));
    }
}
