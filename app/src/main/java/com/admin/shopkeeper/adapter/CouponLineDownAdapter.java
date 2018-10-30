package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.entity.MansongBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class CouponLineDownAdapter extends BaseQuickAdapter<CouponLineDownBean, BaseViewHolder> {
    public CouponLineDownAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponLineDownBean item) {
        helper.setText(R.id.coupon_line_name, item.getName());
        helper.setText(R.id.coupon_line_nums, String.valueOf(item.getCounts()));
        helper.setText(R.id.coupon_line_max_use, String.valueOf(item.getMaxUseCount()));
        helper.setText(R.id.coupon_free_money, item.getPice()+"");
    }
}
