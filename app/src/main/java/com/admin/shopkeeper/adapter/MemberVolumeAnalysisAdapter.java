package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MemberVolumeAnalysisBean;
import com.admin.shopkeeper.entity.RechargeDetailTableBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class MemberVolumeAnalysisAdapter extends BaseQuickAdapter<MemberVolumeAnalysisBean, BaseViewHolder> {
    public MemberVolumeAnalysisAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberVolumeAnalysisBean item) {
        helper.setText(R.id.item_name, App.INSTANCE().getShopName());
        helper.setText(R.id.item_date, item.getDate());
        helper.setText(R.id.item_add_member, String.valueOf(item.getTotal()));
        helper.setText(R.id.item_wechat, String.valueOf(item.getWeChat()));
        helper.setText(R.id.item_line, String.valueOf(item.getLineOfDown()));
    }
}
