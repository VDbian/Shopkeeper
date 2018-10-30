package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by guxiaogasumi on 2017/6/18.
 */

public class MenuTypeAdapter extends BaseQuickAdapter<MenuTypeEntity,BaseViewHolder> {
    public MenuTypeAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuTypeEntity item) {
        helper.setText(R.id.roomName, item.getProductTypeName());
    }
}
