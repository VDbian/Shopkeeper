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
import com.admin.shopkeeper.entity.RechargeManageBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberLevelManage.MemberLevelManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeManage.RechargeManageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */
public class RechargeManagerAdapter extends  RecyclerView.Adapter<RechargeManagerAdapter.ViewHolder> {

    List<RechargeManageBean> datas = new ArrayList<>();
    Activity context;
    private final LayoutInflater inflater;

    public RechargeManagerAdapter(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RechargeManagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RechargeManagerAdapter.ViewHolder(inflater.inflate(R.layout.item_recharge_manage, parent, false));
    }

    @Override
    public void onBindViewHolder(RechargeManagerAdapter.ViewHolder holder, int position) {

        RechargeManageBean rechargeManageBean = datas.get(position );
        rechargeManageBean.setPosition(position);

        holder.tvName.setTextColor(Color.parseColor("#333333"));
        holder.tvName.setText(rechargeManageBean.getName());
        holder.tvRechargeMoney.setText(rechargeManageBean.getRechargeMoney());
        holder.tvGiveMoney.setText(rechargeManageBean.getGiveMoney());
        if(rechargeManageBean.getType().equals("1")){
            holder.tvType.setText("金额");
        }else{
            holder.tvType.setText("积分");
        }

        holder.llRoot.setOnClickListener(v -> {
            ((RechargeManageActivity) context).showDeletePop(rechargeManageBean);
        });
    }

    @Override
    public int getItemCount() {
        return datas.size() ;
    }

    public void setDatas(List<RechargeManageBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void delete(RechargeManageBean bean) {
        datas.remove(bean);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvRechargeMoney;
        TextView tvGiveMoney;
        TextView tvType;

        LinearLayout llRoot;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.item_name);
            tvRechargeMoney = (TextView) itemView.findViewById(R.id.item_recharge_money);
            tvGiveMoney = (TextView) itemView.findViewById(R.id.item_give_money);
            tvType = (TextView) itemView.findViewById(R.id.item_type);
            llRoot = (LinearLayout) itemView.findViewById(R.id.item_root);
        }
    }
}
