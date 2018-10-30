/**
 * created by jiang, 12/3/15
 * Copyright (c) 2015, jyuesong@gmail.com All Rights Reserved.
 * *                #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 */

package com.admin.shopkeeper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.ui.activity.BigImageActivity;
import com.bumptech.glide.Glide;
import com.jiang.android.lib.widget.SwipeItemLayout;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by jiang on 12/3/15.
 * 根据当前权限进行判断相关的滑动逻辑
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    Context mContext;
    OnItemClickLishener lishener;
    List<FoodEntity> mDatas = new ArrayList<>();
    /**
     * 当前处于打开状态的item
     */
    private List<SwipeItemLayout> mOpenedSil = new ArrayList<>();

    public ContactAdapter(Context context, List<FoodEntity> mLists, OnItemClickLishener lishener) {
        mDatas.addAll(mLists);
        mContext = context;
        this.lishener = lishener;
    }

    public ContactAdapter(Context context, OnItemClickLishener lishener) {
        mContext = context;
        this.lishener = lishener;
    }

    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderfood, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public FoodEntity getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ContactViewHolder holder, final int position) {
        FoodEntity item = getItem(position);
        SwipeItemLayout swipeRoot = holder.mRoot;
        swipeRoot.setSwipeAble(true);
        swipeRoot.setDelegate(new SwipeItemLayout.SwipeItemLayoutDelegate() {
            @Override
            public void onSwipeItemLayoutOpened(SwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
                mOpenedSil.add(swipeItemLayout);
            }

            @Override
            public void onSwipeItemLayoutClosed(SwipeItemLayout swipeItemLayout) {
                mOpenedSil.remove(swipeItemLayout);
            }

            @Override
            public void onSwipeItemLayoutStartOpen(SwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
            }
        });

        Glide.with(mContext).load(Config.BASE_IMG + App.INSTANCE().getShopID() + "/" + item.getProductFile())
                .centerCrop().into(holder.imageView);

        if (item.getType()) {
            holder.tvFoodName.setText(item.getPackageName());
            holder.tvUnit.setText(String.format(mContext.getString(R.string.string_unit), "份"));
        } else {
            holder.tvFoodName.setText(item.getProductName());
            holder.tvUnit.setText(String.format(mContext.getString(R.string.string_unit), item.getUnit()));
        }

//        holder.tvPrice.setText(String.format(mContext.getString(R.string.string_money), item.getPrice()));
        holder.tvPrice.setText(item.getPrice()+"("+item.getMemberPice()+")");

        holder.badgeView.setBadgeNumber(0);
        if (!item.getProductShuXing().equals("0")) { //1是称斤 2是规格菜品 0是默认菜品
            holder.clLayout.setVisibility(View.GONE);
            holder.flLayout.setVisibility(View.VISIBLE);
        } else {
            if(item.getTasteType() == null){
                holder.clLayout.setVisibility(View.VISIBLE);
                holder.flLayout.setVisibility(View.GONE);
                if (item.getNumber() > 0) {
                    holder.ibReduce.setVisibility(View.VISIBLE);
                    holder.tvNumber.setVisibility(View.VISIBLE);
                    holder.tvNumber.setText(String.valueOf(item.getNumber()));
                } else {
                    holder.ibReduce.setVisibility(View.INVISIBLE);
                    holder.tvNumber.setVisibility(View.INVISIBLE);
                }
            }else{
                if (item.getTasteType().equals("1")) {
                    holder.clLayout.setVisibility(View.GONE);
                    holder.flLayout.setVisibility(View.VISIBLE);
                    holder.badgeView.setBadgeNumber(item.getNumber());
                } else {
                    holder.clLayout.setVisibility(View.VISIBLE);
                    holder.flLayout.setVisibility(View.GONE);
                    if (item.getNumber() > 0) {
                        holder.ibReduce.setVisibility(View.VISIBLE);
                        holder.tvNumber.setVisibility(View.VISIBLE);
                        holder.tvNumber.setText(String.valueOf(item.getNumber()));
                    } else {
                        holder.ibReduce.setVisibility(View.INVISIBLE);
                        holder.tvNumber.setVisibility(View.INVISIBLE);
                    }
                }
            }

        }
        //state=1上架
        if (item.getState() == 2) {
            holder.ibadd.setVisibility(View.INVISIBLE);
            holder.button.setVisibility(View.INVISIBLE);
        } else {
            holder.ibadd.setVisibility(View.VISIBLE);
            holder.button.setVisibility(View.VISIBLE);
        }

        holder.imageView.setOnClickListener(v -> {
            closeOpenedSwipeItemLayoutWithAnim();
            Intent intent = new Intent(mContext, BigImageActivity.class);
            intent.putExtra(Config.PARAM1, item.getProductFile());
            mContext.startActivity(intent);
        });
        holder.ibadd.setOnClickListener(v -> {
            closeOpenedSwipeItemLayoutWithAnim();
            if (lishener != null) {
                lishener.add(item, position);
            }
        });
        holder.ibReduce.setOnClickListener(v -> {
            closeOpenedSwipeItemLayoutWithAnim();
            if (lishener != null) {
                lishener.reduce(item, position);
            }
        });
        holder.button.setOnClickListener(v -> {
            closeOpenedSwipeItemLayoutWithAnim();
            if (lishener != null) {
                lishener.button(item, position);
            }
        });
        holder.tvDelete.setOnClickListener(v -> {
            closeOpenedSwipeItemLayoutWithAnim();
            if (lishener != null) {
                lishener.put(item, position);
            }
            item.setState(2);
            notifyDataSetChanged();
        });
        holder.tvAdd.setOnClickListener(v -> {
            closeOpenedSwipeItemLayoutWithAnim();
            if (lishener != null) {
                lishener.push(item, position);
            }
            item.setState(1);
            notifyDataSetChanged();

        });
        holder.tvAllAdd.setOnClickListener(v -> {
            closeOpenedSwipeItemLayoutWithAnim();
            if (lishener != null) {
                lishener.pushAll(item, position);
            }
            notifyDataSetChanged();
        });
    }

    public List<FoodEntity> getDatas() {
        return mDatas;
    }

    public void setDatas(List<FoodEntity> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (SwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        SwipeItemLayout mRoot;
        TextView tvAllAdd;
        TextView tvAdd;
        TextView tvDelete;

        AppCompatImageView imageView;
        AppCompatTextView tvFoodName;
        AppCompatTextView tvInventory;
        AppCompatTextView tvPrice;
        AppCompatTextView tvUnit;
        ConstraintLayout clLayout;
        AppCompatImageButton ibReduce;
        AppCompatImageButton ibadd;
        AppCompatTextView tvNumber;
        FrameLayout flLayout;
        AppCompatButton button;

        QBadgeView badgeView;

        public ContactViewHolder(View itemView) {
            super(itemView);

            mRoot = (SwipeItemLayout) itemView.findViewById(R.id.item_contact_swipe_root);

            tvAllAdd = (TextView) itemView.findViewById(R.id.item_menu_alladd);
            tvAdd = (TextView) itemView.findViewById(R.id.item_menu_add);
            tvDelete = (TextView) itemView.findViewById(R.id.item_menu_delete);

            imageView = (AppCompatImageView) itemView.findViewById(R.id.imageView);
            tvFoodName = (AppCompatTextView) itemView.findViewById(R.id.foodName);
            tvInventory = (AppCompatTextView) itemView.findViewById(R.id.inventory);
            tvPrice = (AppCompatTextView) itemView.findViewById(R.id.price);
            tvUnit = (AppCompatTextView) itemView.findViewById(R.id.unit);
            clLayout = (ConstraintLayout) itemView.findViewById(R.id.layout);
            ibReduce = (AppCompatImageButton) itemView.findViewById(R.id.reduce);
            ibadd = (AppCompatImageButton) itemView.findViewById(R.id.add);
            tvNumber = (AppCompatTextView) itemView.findViewById(R.id.numberText);
            flLayout = (FrameLayout) itemView.findViewById(R.id.root);
            button = (AppCompatButton) itemView.findViewById(R.id.button);

            badgeView = new QBadgeView(mContext);
            badgeView.bindTarget(flLayout);
            badgeView.setBadgeGravity(Gravity.TOP | Gravity.END);
        }
    }


    public interface OnItemClickLishener {
        void add(FoodEntity entity, int position);

        void reduce(FoodEntity entity, int position);

        void button(FoodEntity entity, int position);

        void pushAll(FoodEntity entity, int position);

        void push(FoodEntity entity, int position);

        void put(FoodEntity entity, int position);
    }
}
