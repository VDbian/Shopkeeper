package com.admin.shopkeeper.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CouponLineChoiceAdapter extends BaseQuickAdapter<CouponLineDownBean, BaseViewHolder> {


    public CouponLineChoiceAdapter(@LayoutRes int layoutResId, @Nullable List<CouponLineDownBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponLineDownBean item) {
        helper.setText(R.id.item_coupon_name, item.getName()+"(优惠："+item.getPice()+")");
        helper.addOnClickListener(R.id.item_delete);
    }
}
