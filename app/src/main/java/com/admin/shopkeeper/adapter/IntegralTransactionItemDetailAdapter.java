package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.IntegralTranscationItemDetailBean;
import com.admin.shopkeeper.entity.RechargeTranscationItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class IntegralTransactionItemDetailAdapter extends BaseQuickAdapter<IntegralTranscationItemDetailBean, BaseViewHolder> {
    public IntegralTransactionItemDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralTranscationItemDetailBean item) {
        if(item.getConsumeType() != null){
            helper.setText(R.id.item_consume_type, item.getConsumeType());
        }
        if(item.getPrice() != null){
            helper.setText(R.id.item_price, item.getPrice());
        }
        helper.setText(R.id.item_time, item.getTime());
    }
}
