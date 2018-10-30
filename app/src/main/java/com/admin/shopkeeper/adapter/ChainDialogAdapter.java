package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.MutiBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class ChainDialogAdapter extends BaseQuickAdapter<ChainBean, BaseViewHolder> {


    public ChainDialogAdapter(@LayoutRes int layoutResId, @Nullable List<ChainBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChainBean item) {
        helper.setText(R.id.textView, item.getNames());
        helper.setChecked(R.id.checkbox, item.isSelect());
    }
}
