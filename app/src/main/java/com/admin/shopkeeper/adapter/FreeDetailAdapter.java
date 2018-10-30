package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FreeDetailBean;
import com.admin.shopkeeper.entity.HandoverDetailBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class FreeDetailAdapter extends BaseQuickAdapter<FreeDetailBean, BaseViewHolder> {
    public FreeDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FreeDetailBean item) {
        helper.setText(R.id.item_paytype, item.getPayType());
        helper.setText(R.id.item_money, "￥" + item.getPice());
        helper.setText(R.id.item_count, item.getCount());
    }
}
