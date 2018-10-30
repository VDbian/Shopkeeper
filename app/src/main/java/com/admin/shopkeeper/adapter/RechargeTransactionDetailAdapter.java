package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.CollectionBean;
import com.admin.shopkeeper.entity.RechargeTranscationItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class RechargeTransactionDetailAdapter extends BaseQuickAdapter<RechargeTranscationItemBean, BaseViewHolder> {
    public RechargeTransactionDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeTranscationItemBean item) {
        helper.setText(R.id.item_consume_type, item.getConsumeType());
        helper.setText(R.id.item_paytype, item.getPayType());
        helper.setText(R.id.item_price, item.getPrice());
        helper.setText(R.id.item_time, item.getTime());
    }
}
