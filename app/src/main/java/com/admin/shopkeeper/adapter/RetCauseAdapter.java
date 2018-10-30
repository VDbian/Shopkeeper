package com.admin.shopkeeper.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.returncause.ReturnCauseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class RetCauseAdapter extends RecyclerView.Adapter<RetCauseAdapter.ViewHolder> {

    List<RetCauseBean> datas = new ArrayList<>();
    Activity context;
    private final LayoutInflater inflater;

    public RetCauseAdapter(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_retcause, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            holder.tvId.setTextColor(Color.parseColor("#888888"));
            holder.tvCause.setTextColor(Color.parseColor("#888888"));
            holder.tvId.setText("序号");
            holder.tvCause.setText("退菜原因");
            holder.llRoot.setOnClickListener(null);
            return;
        }

        RetCauseBean retCauseBean = datas.get(position - 1);
        holder.tvId.setTextColor(Color.parseColor("#333333"));
        holder.tvCause.setTextColor(Color.parseColor("#333333"));
        holder.tvId.setText(position + "");
        holder.tvCause.setText(retCauseBean.getRemark());

        holder.llRoot.setOnClickListener(v -> {
            ((ReturnCauseActivity) context).showDeletePop(retCauseBean);
        });
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    public void setDatas(List<RetCauseBean> datas) {
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
        TextView tvCause;
        LinearLayout llRoot;

        public ViewHolder(View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.item_id);
            tvCause = (TextView) itemView.findViewById(R.id.item_cause);
            llRoot = (LinearLayout) itemView.findViewById(R.id.item_root);
        }
    }
}
