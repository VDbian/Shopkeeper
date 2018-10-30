package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MansongBean;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class MansongAdapter extends BaseQuickAdapter<MansongBean, BaseViewHolder> {
    public MansongAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MansongBean item) {
        helper.setText(R.id.item_sortno, getData().indexOf(item) + 1 + "");
        helper.setText(R.id.item_name, item.getName());
        helper.setText(R.id.item_date, item.getbTime().replace("00:00:00","").trim() + "\n" + item.geteTime().replace("23:59:59","").trim());
        if (TextUtils.isEmpty(item.getApply())) {
            helper.setText(R.id.item_type, "");
        } else if (item.getApply().equals("1")) {
            helper.setText(R.id.item_type, "预定");
        } else if (item.getApply().equals("3")) {
            helper.setText(R.id.item_type, "外卖");
        } else if (item.getApply().equals("4")) {
            helper.setText(R.id.item_type, "快餐");
        } else if (item.getApply().equals("5")) {
            helper.setText(R.id.item_type, "扫码点餐");
        }
    }
}
