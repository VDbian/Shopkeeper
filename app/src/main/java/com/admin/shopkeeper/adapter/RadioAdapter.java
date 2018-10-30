package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.RadioEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class RadioAdapter extends BaseQuickAdapter<RadioEntity, BaseViewHolder> {
    public RadioAdapter(@LayoutRes int layoutResId, @Nullable List<RadioEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RadioEntity item) {
        helper.getView(R.id.name).setSelected(item.isSelected());
        helper.setText(R.id.name, item.getStr());
    }
}
