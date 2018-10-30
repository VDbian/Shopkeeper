package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MutiBean;
import com.admin.shopkeeper.entity.ShopBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class ShopDialogAdapter extends BaseQuickAdapter<ShopBean, BaseViewHolder> {


    public ShopDialogAdapter(@LayoutRes int layoutResId, @Nullable List<ShopBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopBean item) {
        helper.setText(R.id.textView, item.getName());
        helper.setChecked(R.id.checkbox, item.isSelect());
    }
}
