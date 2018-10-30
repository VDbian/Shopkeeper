package com.admin.shopkeeper.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.SaleBean;
import com.admin.shopkeeper.entity.WeightBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class WeightAdapter extends BaseQuickAdapter<WeightBean, BaseViewHolder> {


    public WeightAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeightBean item) {
        helper.setText(R.id.item_name, item.getName());
        helper.setText(R.id.item_price, item.getPrice());
        helper.setText(R.id.item_weight, item.getWeight());
        helper.setText(R.id.item_dev, item.getDeviation());
    }
}
