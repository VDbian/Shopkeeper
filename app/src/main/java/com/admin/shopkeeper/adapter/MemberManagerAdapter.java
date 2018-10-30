package com.admin.shopkeeper.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MemberInfoBean;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberManager.MemberManageActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */
public class MemberManagerAdapter extends  RecyclerView.Adapter<MemberManagerAdapter.ViewHolder> {

    List<MemberInfoBean> datas = new ArrayList<>();
    Activity context;
    private final LayoutInflater inflater;

    public MemberManagerAdapter(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MemberManagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberManagerAdapter.ViewHolder(inflater.inflate(R.layout.item_member_manage, parent, false));
    }

    @Override
    public void onBindViewHolder(MemberManagerAdapter.ViewHolder holder, int position) {

        MemberInfoBean memberInfoBean = datas.get(position );
        memberInfoBean.setPosition(position);

        holder.tvId.setTextColor(Color.parseColor("#333333"));
        holder.tvName.setTextColor(Color.parseColor("#333333"));
        holder.tvId.setText(position + 1+ "");
        holder.tvName.setText(memberInfoBean.getMemberDepart());
        if(!TextUtils.isEmpty(memberInfoBean.getMemberCard())){
            holder.tvState.setText("已绑定");
            memberInfoBean.setState(1);
        }else{
            holder.tvState.setText("未绑定");
            memberInfoBean.setState(0);
        }
        holder.tvPhone.setText(memberInfoBean.getMemberTel());

        holder.llRoot.setOnClickListener(v -> {
            ((MemberManageActivity) context).showDeletePop(memberInfoBean);
        });
    }

    @Override
    public int getItemCount() {
        return datas.size() ;
    }

    public void setDatas(List<MemberInfoBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void delete(RetCauseBean bean) {
        datas.remove(bean);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvId;
        TextView tvName;
        TextView tvState;
        TextView tvPhone;

        LinearLayout llRoot;

        public ViewHolder(View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.item_id);
            tvName = (TextView) itemView.findViewById(R.id.item_name);
            tvState = (TextView) itemView.findViewById(R.id.item_state);
            tvPhone = (TextView) itemView.findViewById(R.id.item_phone);
            llRoot = (LinearLayout) itemView.findViewById(R.id.item_root);
        }
    }
}
