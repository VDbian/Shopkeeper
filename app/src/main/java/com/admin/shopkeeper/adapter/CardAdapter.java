package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.CardBean;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class CardAdapter extends BaseQuickAdapter<CardBean, BaseViewHolder> {


    public CardAdapter(@LayoutRes int layoutResId, @Nullable List<CardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CardBean item) {
        helper.setText(R.id.item_name, item.getName());
        if (item.getType().equals("1")) {
            helper.setBackgroundRes(R.id.item_img, R.mipmap.voucher_youhui);
            helper.setText(R.id.item_type, "优惠券");
        } else if (item.getType().equals("2")) {
            helper.setBackgroundRes(R.id.item_img, R.mipmap.voucher_daijing);
            helper.setText(R.id.item_type, "代金券");
        } else if (item.getType().equals("3")) {
            helper.setBackgroundRes(R.id.item_img, R.mipmap.voucher_shangping);
            helper.setText(R.id.item_type, "商品");
        } else {
            helper.setBackgroundRes(R.id.item_img, R.mipmap.voucher_tuangou);
            helper.setText(R.id.item_type, "团购券");
        }
    }
}
