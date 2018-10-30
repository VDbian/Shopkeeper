package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.admin.shopkeeper.entity.SaleStatisticsProductBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class SaleStatisticsProductAdapter extends BaseQuickAdapter<SaleStatisticsProductBean, BaseViewHolder> {
    public SaleStatisticsProductAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SaleStatisticsProductBean item) {
        helper.setText(R.id.item_product_name, item.getProductName());
    }
}
