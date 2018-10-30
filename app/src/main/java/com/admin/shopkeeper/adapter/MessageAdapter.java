package com.admin.shopkeeper.adapter;

import com.admin.shopkeeper.entity.Message;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zeng on 2017/4/17.
 */

public class MessageAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {
    public MessageAdapter(int layoutResId, List<Message> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {

    }
}
