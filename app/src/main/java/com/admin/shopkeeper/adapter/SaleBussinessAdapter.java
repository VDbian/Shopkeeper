package com.admin.shopkeeper.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodBussinessBean;
import com.admin.shopkeeper.entity.MemberInfoBean;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberManager.MemberManageActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */
public class SaleBussinessAdapter extends  RecyclerView.Adapter<SaleBussinessAdapter.ViewHolder> {

    List<FoodBussinessBean> datas = new ArrayList<>();
    Activity context;
    private final LayoutInflater inflater;

    public SaleBussinessAdapter(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SaleBussinessAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SaleBussinessAdapter.ViewHolder(inflater.inflate(R.layout.item_foodbussiness, parent, false));
    }

    @Override
    public void onBindViewHolder(SaleBussinessAdapter.ViewHolder holder, int position) {

        FoodBussinessBean foodBussinessBean = datas.get(position );

        holder.tvId.setTextColor(Color.parseColor("#333333"));
        holder.tvName.setTextColor(Color.parseColor("#333333"));
        holder.tvId.setText(position + 1+ "");
        holder.tvName.setText(foodBussinessBean.getProductName());
        holder.tvNum.setText(foodBussinessBean.getCounts()+"");
        holder.tvMoney.setText(foodBussinessBean.getPrice()+"");

    }

    @Override
    public int getItemCount() {
        return datas.size() ;
    }

    public void setDatas(List<FoodBussinessBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvId;
        TextView tvName;
        TextView tvNum;
        TextView tvMoney;

        public ViewHolder(View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.item_ranking);
            tvName = (TextView) itemView.findViewById(R.id.item_name);
            tvNum = (TextView) itemView.findViewById(R.id.item_count);
            tvMoney = (TextView) itemView.findViewById(R.id.item_price);
        }
    }
/*public class SaleBussinessAdapter extends BaseQuickAdapter<FoodBussinessBean, BaseViewHolder> {


    public SaleBussinessAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodBussinessBean item) {

        int i = 1;
        helper.setText(R.id.item_ranking,  i++ +"");
        helper.setText(R.id.item_name, item.getProductName() + "");
        helper.setText(R.id.item_count, item.getCounts() + "");
        helper.setText(R.id.item_price, item.getPrice() + "");
    }*/

}
