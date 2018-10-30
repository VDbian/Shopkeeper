package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.QueueBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/9/20.
 */

public class QueueAdapter extends BaseQuickAdapter<QueueBean, BaseViewHolder> {

    public QueueAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, QueueBean item) {
        helper.setText(R.id.item_no, item.getNo());
        helper.setText(R.id.item_name, "姓名：" + item.getUsername());
        helper.setText(R.id.item_phone, "手机：" + item.getPhone());
        helper.setText(R.id.item_table, "桌位：" + item.getTableName());
    }
}
