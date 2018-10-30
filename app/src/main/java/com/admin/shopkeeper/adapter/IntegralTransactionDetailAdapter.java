package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.IntegralDetailTableBean;
import com.admin.shopkeeper.entity.MemberConsumeDetailBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class IntegralTransactionDetailAdapter extends BaseQuickAdapter<IntegralDetailTableBean, BaseViewHolder> {
    public IntegralTransactionDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralDetailTableBean item) {
        helper.setText(R.id.item_name, App.INSTANCE().getShopName());
        helper.setText(R.id.item_username, item.getUserName());
        helper.setText(R.id.item_phone, item.getPhone());
        helper.setText(R.id.item_cumulative_additions, item.getRechargeAmount());
        helper.setText(R.id.item_consume_integral, item.getConsumeAmount());
        helper.setText(R.id.item_add_integral, item.getAdd());
        helper.setText(R.id.item_reduce_integral, item.getReduce());
        helper.setText(R.id.item_integral_balance, item.getYue());
    }
}
