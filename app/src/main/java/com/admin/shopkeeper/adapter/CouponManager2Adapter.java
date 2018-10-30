package com.admin.shopkeeper.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class CouponManager2Adapter extends BaseQuickAdapter<CouponManageBean, BaseViewHolder> {
    public CouponManager2Adapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponManageBean item) {

        switch (item.getTypeId()) {
            case "1":
                helper.setText(R.id.item_num, "满送券");
                break;
            case "2":
                helper.setText(R.id.item_num, "代金券");
                break;
            case "3":
                helper.setText(R.id.item_num, "商品券");
                break;
            case "4":
                helper.setText(R.id.item_num, "团购券");
                break;
            default:
                break;
        }
        helper.setText(R.id.item_name, item.getName());
        helper.setText(R.id.item_coupon_money, item.getEndTime().replace("23:59:59", "").trim());
        helper.setText(R.id.item_need_money, item.getOpearationName());
    }
}
