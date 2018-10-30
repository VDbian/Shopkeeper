package com.admin.shopkeeper.adapter;

import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.admin.shopkeeper.R;

import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by guxiaogasumi on 2017/4/24.
 */

public class MenuClassAdapter extends BaseQuickAdapter<MenuTypeEntity, MenuClassAdapter.ViewHolder> {
    public MenuClassAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ViewHolder helper, MenuTypeEntity item) {
        Log.i("ttt", "----convert---" + item.getProductTypeName());
        helper.setText(R.id.textView, item.getProductTypeName());
        helper.getView(R.id.textView).setSelected(item.isSelected());
        helper.badgeView.setBadgeNumber(item.getCount());
    }

    class ViewHolder extends BaseViewHolder {
        QBadgeView badgeView;

        public ViewHolder(View view) {
            super(view);
            badgeView = new QBadgeView(mContext);
            badgeView.bindTarget(view.findViewById(R.id.root));
            badgeView.setBadgeGravity(Gravity.TOP | Gravity.END);
        }
    }


}
