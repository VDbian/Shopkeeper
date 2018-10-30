package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MemberConsumeDetailBean;
import com.admin.shopkeeper.entity.MemberTranscationBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class MemberConsumeDetailAdapter extends BaseQuickAdapter<MemberConsumeDetailBean, BaseViewHolder> {
    public MemberConsumeDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberConsumeDetailBean item) {
        helper.setText(R.id.item_name, App.INSTANCE().getShopName());
        helper.setText(R.id.item_date, String.valueOf(item.getDate()));
        helper.setText(R.id.item_username, String.valueOf(item.getUserName()));
        helper.setText(R.id.item_phone, item.getPhone());
        helper.setText(R.id.item_consume_time, item.getDate());
        helper.setText(R.id.item_consume_money, String.valueOf(item.getPrice()));
        helper.setText(R.id.item_consume_times, String.valueOf(item.getCounts()));
    }
}
