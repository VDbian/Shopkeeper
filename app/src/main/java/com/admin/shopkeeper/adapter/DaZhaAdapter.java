package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.DaZheEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class DaZhaAdapter extends BaseQuickAdapter<DaZheEntity, BaseViewHolder> {


    public DaZhaAdapter(@LayoutRes int layoutResId, @Nullable List<DaZheEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DaZheEntity item) {
        helper.setText(R.id.textView,  item.getName());
    }
}
