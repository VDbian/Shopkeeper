package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.widget.CheckBox;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.dialog.SetFoodDialog;
import com.admin.shopkeeper.entity.FoodBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class SetFoodAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {


    public SetFoodAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodBean item) {
        helper.setText(R.id.item_name, item.getProductName());
        helper.setText(R.id.item_type, item.getProductTypeName());
        helper.setText(R.id.item_price, item.getPrice() + "");

        CheckBox checkBox = helper.getView(R.id.item_check);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                SetFoodDialog.Builder builder = new SetFoodDialog.Builder(helper.getConvertView().getContext(), R.style.OrderDialogStyle);
                builder.setName(item.getProductName());
                builder.setButtonClick(new SetFoodDialog.OnButtonClick() {
                    @Override
                    public void onBtnClick(int i) {
                        item.setCount(i);
                        helper.setText(R.id.item_count, i + "");
                    }

                    @Override
                    public void onCancel() {
                        checkBox.setChecked(false);
                        //item.setCount(0);
                        //helper.setText(R.id.item_count, "未添加");
                    }
                });
                builder.creater().show();
            } else {
                item.setCount(0);
                helper.setText(R.id.item_count, "未添加");
            }
        });
        if (item.getCount() == 0) {
            helper.setText(R.id.item_count, "未添加");
            checkBox.setChecked(false);
        } else {
            helper.setText(R.id.item_count, item.getCount() + "");
            checkBox.setChecked(true);
        }

//        ImageView imageView = helper.getView(R.id.item_image);
//        Glide.with(mContext)
//                .load(Config.BASE_IMG + App.INSTANCE().getShopID() + "/" + item.getProductFile())
//                .centerCrop().into(imageView);


//        imageView.setOnClickListener(v -> {
//            Intent intent = new Intent(mContext, BigImageActivity.class);
//            intent.putExtra(Config.PARAM1, item.getProductFile());
//            mContext.startActivity(intent);
//        });
    }
}
