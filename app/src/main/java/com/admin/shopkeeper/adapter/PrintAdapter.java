package com.admin.shopkeeper.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.PrintBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class PrintAdapter extends BaseQuickAdapter<PrintBean, BaseViewHolder> {


    public PrintAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PrintBean item) {
        helper.setTextColor(R.id.item_id, Color.parseColor("#333333"));
        helper.setTextColor(R.id.item_name, Color.parseColor("#333333"));
        helper.setTextColor(R.id.item_shop, Color.parseColor("#333333"));
        helper.setTextColor(R.id.item_ip, Color.parseColor("#333333"));

        helper.setText(R.id.item_id, item.getId());
        helper.setText(R.id.item_shop, item.getShangjia());
        helper.setText(R.id.item_name, item.getName());
        helper.setText(R.id.item_ip, item.getIpAddress() + ":" + item.getPort());
    }
}
