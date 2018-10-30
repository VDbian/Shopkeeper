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
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.MemberLevelManageBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.couponManage.CouponManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberLevelManage.MemberLevelManageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */
public class CouponManagerAdapter extends  RecyclerView.Adapter<CouponManagerAdapter.ViewHolder> {

    List<CouponManageBean> datas = new ArrayList<>();
    Activity context;
    private final LayoutInflater inflater;

    public CouponManagerAdapter(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CouponManagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CouponManagerAdapter.ViewHolder(inflater.inflate(R.layout.item_coupon_manage, parent, false));
    }

    @Override
    public void onBindViewHolder(CouponManagerAdapter.ViewHolder holder, int position) {

        CouponManageBean couponManageBean = datas.get(position );
        couponManageBean.setPosition(position);

        holder.tvNum.setTextColor(Color.parseColor("#333333"));
        holder.tvCouponMoney.setTextColor(Color.parseColor("#333333"));
        switch (couponManageBean.getTypeId()){
            case"1":
                holder.tvNum.setText("满送券");
                break;
            case"2":
                holder.tvNum.setText("代金券");
                break;
            case"3":
                holder.tvNum.setText("商品券");
                break;
            case"4":
                holder.tvNum.setText("团购券");
                break;
            default:
                break;
        }
        holder.tvName.setText(couponManageBean.getName());
        holder.tvCouponMoney.setText(couponManageBean.getEndTime());
        holder.tvNeedMoney.setText(couponManageBean.getOpearationName());

        holder.llRoot.setOnClickListener(v -> {
            ((CouponManageActivity) context).showDeletePop(couponManageBean);
        });
    }

    @Override
    public int getItemCount() {
        return datas.size() ;
    }

    public void setDatas(List<CouponManageBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void delete(MemberLevelManageBean bean) {
        datas.remove(bean);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNum;
        TextView tvName;
        TextView tvCouponMoney;
        TextView tvNeedMoney;

        LinearLayout llRoot;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNum = (TextView) itemView.findViewById(R.id.item_num);
            tvName = (TextView) itemView.findViewById(R.id.item_name);
            tvCouponMoney = (TextView) itemView.findViewById(R.id.item_coupon_money);
            tvNeedMoney = (TextView) itemView.findViewById(R.id.item_need_money);
            llRoot = (LinearLayout) itemView.findViewById(R.id.item_root);
        }
    }
}
