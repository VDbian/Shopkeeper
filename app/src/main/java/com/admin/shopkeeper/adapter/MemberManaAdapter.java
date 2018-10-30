package com.admin.shopkeeper.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MemberInfoBean;
import com.admin.shopkeeper.entity.SaleBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberManager.MemberManageActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class MemberManaAdapter extends BaseQuickAdapter<MemberInfoBean, BaseViewHolder> {


    public MemberManaAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberInfoBean item) {

        helper.setText(R.id.item_id, (getData().indexOf(item) + 1) + "");
        helper.setText(R.id.item_name,item.getMemberDepart());
        if (!TextUtils.isEmpty(item.getMemberCard())) {
            helper.setText(R.id.item_state,"已绑定");
            item.setState(1);
        } else {
            helper.setText(R.id.item_state,"未绑定");
            item.setState(0);
        }
        helper.setText(R.id.item_phone,item.getMemberTel());

//        helper.llRoot.setOnClickListener(v -> {
//            ((MemberManageActivity) context).showDeletePop(item);
//        });
    }
}
