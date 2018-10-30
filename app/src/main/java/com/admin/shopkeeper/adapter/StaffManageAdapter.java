package com.admin.shopkeeper.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MemberInfoBean;
import com.admin.shopkeeper.entity.ShopPermissionManageBean;
import com.admin.shopkeeper.entity.StaffManageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public class StaffManageAdapter extends BaseQuickAdapter<StaffManageBean, BaseViewHolder> {

    List<ShopPermissionManageBean> datas = new ArrayList<>();
    Activity context;

    public StaffManageAdapter(@LayoutRes int layoutResId, @Nullable List<StaffManageBean> data) {
        super(layoutResId, data);
    }

    public StaffManageAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, StaffManageBean item) {
        helper.setText(R.id.item_name, item.getUserName() + "");
        helper.setText(R.id.item_password, item.getPassword() + "");
        helper.setText(R.id.item_time, item.getCreateTime());

        helper.setTextColor(R.id.item_name, Color.parseColor("#333333"));
        helper.setTextColor(R.id.item_password, Color.parseColor("#333333"));
        helper.setTextColor(R.id.item_time, Color.parseColor("#333333"));

    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

}
