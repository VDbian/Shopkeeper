package com.admin.shopkeeper.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.PrintBean;
import com.admin.shopkeeper.entity.SaleBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class SaleAdapter extends BaseQuickAdapter<SaleBean, BaseViewHolder> {


    public SaleAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SaleBean item) {
        helper.setTextColor(R.id.item_id, Color.parseColor("#333333"));
        helper.setTextColor(R.id.item_name, Color.parseColor("#333333"));
        helper.setTextColor(R.id.item_count, Color.parseColor("#333333"));

        helper.setText(R.id.item_id, item.getGuiId());
        helper.setText(R.id.item_count, item.getCount());
        helper.setText(R.id.item_name, item.getName());
    }
}
