package com.admin.shopkeeper.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.ui.activity.bill.BillActivity;
import com.admin.shopkeeper.ui.activity.login.LoginActivity;
import com.admin.shopkeeper.utils.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import es.dmoral.toasty.Toasty;

/**
 * Created by zeng on 2017/4/23.
 */
public class MenuListAdapter extends BaseQuickAdapter<OrderDetailFood, BaseViewHolder> {

    Context mContext;
    boolean mIsVisible;

    public MenuListAdapter(int layoutResId, Context context, boolean isVisible) {
        super(layoutResId);
        mContext = context;
        mIsVisible = isVisible;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailFood item) {
        helper.setVisible(R.id.etSale, mIsVisible);
        helper.setText(R.id.name, item.getProductNmae());
        if (item.getGiving() > 0) {
            if(item.getWeight() != 1 && item.getCount() ==1){
                helper.setText(R.id.number, String.valueOf(item.getCount()) + "("+item.getWeight()+item.getUnit()+")"+"(赠送" + item.getGiving() + "份)");
            }else{
                helper.setText(R.id.number, String.valueOf(item.getCount()) + "(赠送" + item.getGiving() + "份)");
            }
        }else {
            if(item.getWeight() != 1 && item.getCount() ==1){
                helper.setText(R.id.number, String.valueOf(item.getCount()) + "("+item.getWeight()+item.getUnit()+")");
            }else{
                helper.setText(R.id.number, String.valueOf(item.getCount()));
            }
        }
        helper.setText(R.id.price, item.getPrice() + "");
        helper.setText(R.id.vipPrice, item.getChargeMoney());
        if (item.getSale() > 0) {
            helper.setText(R.id.etSale, String.valueOf(item.getSale()));
        }
        if (item.getSale() > 0) {
            helper.setText(R.id.etSale, String.valueOf(item.getSale()));
        } else {
            helper.setText(R.id.etSale, "");
        }
        helper.addOnClickListener(R.id.etSale);

        AppCompatTextView textView = helper.getView(R.id.describe);
        textView.setText("");
        if (!TextUtils.isEmpty(item.getSeasonName())) {
            textView.append(item.getSeasonName() + "(￥" + item.getSeasonPrice() + ")、");
        }
        if (!TextUtils.isEmpty(item.getProductshuxin())) {
            textView.append(item.getProductshuxin() + "、");
        }
        if (!TextUtils.isEmpty(item.getRemark())) {
            textView.append(item.getRemark() + "、");
        }
    }
}
