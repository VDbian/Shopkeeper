package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.HandoverDetailBean;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class HandoverDetailAdapter extends BaseQuickAdapter<HandoverDetailBean, BaseViewHolder> {
    public HandoverDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HandoverDetailBean item) {
        helper.setText(R.id.item_name, item.getPayName());
        helper.setText(R.id.item_value, "￥" + String.valueOf(item.getPrice()));
    }
}
