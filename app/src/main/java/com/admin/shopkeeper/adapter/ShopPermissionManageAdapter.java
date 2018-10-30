package com.admin.shopkeeper.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.entity.ShopPermissionManageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ShopPermissionManageAdapter extends BaseQuickAdapter<ShopPermissionManageBean, BaseViewHolder> {

    List<ShopPermissionManageBean> datas = new ArrayList<>();
    Activity context;

    public ShopPermissionManageAdapter(@LayoutRes int layoutResId, @Nullable List<ShopPermissionManageBean> data) {
        super(layoutResId, data);
    }

    public ShopPermissionManageAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopPermissionManageBean item) {
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
