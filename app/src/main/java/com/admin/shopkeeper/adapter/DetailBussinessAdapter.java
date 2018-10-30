package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.DetailBussinessBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/29.
 */

public class DetailBussinessAdapter extends BaseQuickAdapter<DetailBussinessBean, BaseViewHolder> {


    public DetailBussinessAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailBussinessBean item) {
        helper.setText(R.id.item_name, "菜品名称: " + item.getProductName());
        helper.setText(R.id.item_table, "所属桌位: " + item.getTableName());
        helper.setText(R.id.item_user, "退菜人: " + item.getUsername());
        helper.setText(R.id.item_count, "菜品数量: " + item.getCount());
        helper.setText(R.id.item_price, "总金额: " + item.getTotlePrice());
    }

}
