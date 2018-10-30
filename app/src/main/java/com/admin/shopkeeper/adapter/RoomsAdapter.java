package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.RoomEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.greendao.annotation.Entity;

/**
 * Created by guxiaogasumi on 2017/6/17.
 */

public class RoomsAdapter extends BaseQuickAdapter<RoomEntity, BaseViewHolder> {
    public RoomsAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomEntity item) {
        helper.setText(R.id.roomName, item.getName());
    }
}
