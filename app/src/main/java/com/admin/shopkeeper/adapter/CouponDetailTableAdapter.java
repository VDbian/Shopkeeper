package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.CouponDetailTableBean;
import com.admin.shopkeeper.entity.IntegralDetailTableBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class CouponDetailTableAdapter extends BaseQuickAdapter<CouponDetailTableBean, BaseViewHolder> {
    public CouponDetailTableAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponDetailTableBean item) {
        helper.setText(R.id.item_name, App.INSTANCE().getShopName());
        helper.setText(R.id.item_couponname, item.getCouponName());
        helper.setText(R.id.item_get_coupon_person, item.getCouponPerson());
        helper.setText(R.id.item_coupon_give_out, item.getGiveConponVolume());
        helper.setText(R.id.item_coupon_used_volume, item.getUseCoupon());
        helper.setText(R.id.item_coupon_discount, item.getDiscount());
        helper.setText(R.id.item_accumulate_transaction_money, item.getFreePriceByCoupon());
        helper.setText(R.id.item_coupon_chance, item.getUseLv());
        helper.setText(R.id.item_consume_by_coupon, item.getConsumeByCoupon());
    }
}
