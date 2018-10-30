package com.admin.shopkeeper.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CouponLineAdapter extends BaseQuickAdapter<CouponLineDownBean, BaseViewHolder> {


    public CouponLineAdapter(@LayoutRes int layoutResId, @Nullable List<CouponLineDownBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponLineDownBean item) {
        helper.setText(R.id.textView, item.getName());

        if (item.isSelected()) {
            helper.setBackgroundRes(R.id.textView, R.drawable.bg_item_selected);
            helper.setTextColor(R.id.textView, Color.WHITE);
        } else {
            helper.setBackgroundRes(R.id.textView, R.drawable.bg_item_normal);
            helper.setTextColor(R.id.textView, Color.parseColor("#ff2d4b"));
        }
    }
}
