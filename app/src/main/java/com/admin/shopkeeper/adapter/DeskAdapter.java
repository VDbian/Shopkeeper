package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.DeskBussinessBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/29.
 */

public class DeskAdapter extends BaseQuickAdapter<DeskBussinessBean, BaseViewHolder> {


    public DeskAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeskBussinessBean item) {
        helper.setText(R.id.item_desk, item.getTableName() + "");
        helper.setText(R.id.item_count, item.getCounts() + "");
        helper.setText(R.id.item_people, item.getPersion() + "");
        helper.setText(R.id.item_totle, item.getPrice() + "");
    }

}
