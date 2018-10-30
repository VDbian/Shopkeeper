package com.admin.shopkeeper.adapter;

import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.KouWei;
import com.admin.shopkeeper.entity.RetreatReason;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by admin on 2017/6/30.
 */

public class ResaonAdapter extends BaseMultiItemQuickAdapter<RetreatReason, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ResaonAdapter(List<RetreatReason> data) {
        super(data);
        addItemType(KouWei.NORMAL, R.layout.item_order_food_dialog);
        addItemType(KouWei.EDIT, R.layout.item_kouwei_edit);
    }
//    public KouWeiAdapter(@LayoutRes int layoutResId) {
//        super(layoutResId);
//    }

    @Override
    protected void convert(BaseViewHolder helper, RetreatReason item) {

        helper.setText(R.id.textView, TextUtils.isEmpty(item.getRemark()) ? "" : item.getRemark());

        if (item.getItemType() == RetreatReason.EDIT) {

            AppCompatEditText editText = helper.getView(R.id.textView);
            helper.getView(R.id.textView).setSelected(item.isSelector());
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    item.setSelector(!TextUtils.isEmpty(s));
                    item.setRemark(TextUtils.isEmpty(s) ? "" : s.toString());
                    helper.getView(R.id.textView).setSelected(item.isSelector());
                }
            });
        } else if (item.getItemType() == RetreatReason.NORMAL) {
            helper.getView(R.id.textView).setSelected(item.isSelector());
        }
    }
}
