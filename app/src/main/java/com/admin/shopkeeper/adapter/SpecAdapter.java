package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.Spec;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by admin on 2017/6/30.
 */

public class SpecAdapter extends BaseQuickAdapter<Spec, BaseViewHolder> {
    public SpecAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Spec item) {

        helper.setText(R.id.textView, item.getName());
        helper.getView(R.id.textView).setSelected(item.isSelected());
    }
}
