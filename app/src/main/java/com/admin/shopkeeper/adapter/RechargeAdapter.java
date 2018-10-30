package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MemberBean;
import com.admin.shopkeeper.entity.RechargeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class RechargeAdapter extends BaseQuickAdapter<RechargeBean, BaseViewHolder> {


    public RechargeAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeBean item) {
        helper.setText(R.id.item_no, (getData().indexOf(item) + 1) + "");
        helper.setText(R.id.item_name, item.getStaffDepart());
        helper.setText(R.id.item_phone, item.getStaffTel());
        helper.setText(R.id.item_card, item.getStaffCard());
    }
}
