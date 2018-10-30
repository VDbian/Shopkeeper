package com.admin.shopkeeper.adapter;

import android.support.v7.widget.RecyclerView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.Label;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by admin on 2017/4/7.
 */

public class LabelAdapter extends BaseQuickAdapter<Label, BaseViewHolder> {


    public LabelAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Label item) {
        helper.setImageResource(R.id.labelImage, item.getLabelID());
        helper.setText(R.id.labelText, item.getLabelName());
    }
}
