package com.admin.shopkeeper.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MemberLevelManageBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberLevelManage.MemberLevelManageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */
public class MemberLevelManagerAdapter extends  RecyclerView.Adapter<MemberLevelManagerAdapter.ViewHolder> {

    List<MemberLevelManageBean> datas = new ArrayList<>();
    Activity context;
    private final LayoutInflater inflater;

    public MemberLevelManagerAdapter(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MemberLevelManagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberLevelManagerAdapter.ViewHolder(inflater.inflate(R.layout.item_member_level_manage, parent, false));
    }

    @Override
    public void onBindViewHolder(MemberLevelManagerAdapter.ViewHolder holder, int position) {

        MemberLevelManageBean memberLevelManageBean = datas.get(position );
        memberLevelManageBean.setPosition(position);

        holder.tvId.setTextColor(Color.parseColor("#333333"));
        holder.tvName.setTextColor(Color.parseColor("#333333"));
        holder.tvId.setText(position + 1+ "");
        holder.tvName.setText(memberLevelManageBean.getName());
        holder.tvMinimum.setText(memberLevelManageBean.getMinPoints());
        holder.tvMaximum.setText(memberLevelManageBean.getMaxPoints());


        holder.llRoot.setOnClickListener(v -> {
            ((MemberLevelManageActivity) context).showDeletePop(memberLevelManageBean);
        });
    }

    @Override
    public int getItemCount() {
        return datas.size() ;
    }

    public void setDatas(List<MemberLevelManageBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void delete(MemberLevelManageBean bean) {
        datas.remove(bean);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvId;
        TextView tvName;
        TextView tvMinimum;
        TextView tvMaximum;

        LinearLayout llRoot;

        public ViewHolder(View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.item_id);
            tvName = (TextView) itemView.findViewById(R.id.item_name);
            tvMinimum = (TextView) itemView.findViewById(R.id.item_min);
            tvMaximum = (TextView) itemView.findViewById(R.id.item_max);
            llRoot = (LinearLayout) itemView.findViewById(R.id.item_root);
        }
    }
}
