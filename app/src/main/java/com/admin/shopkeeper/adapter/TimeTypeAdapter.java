package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.TimeTypeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class TimeTypeAdapter extends BaseQuickAdapter<TimeTypeBean, BaseViewHolder> {

    private String select;

    public TimeTypeAdapter(@LayoutRes int layoutResId, @Nullable List<TimeTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TimeTypeBean item) {
        helper.setText(R.id.textView, item.getName());
        helper.setChecked(R.id.checkbox, item.isSelect());
    }
}
