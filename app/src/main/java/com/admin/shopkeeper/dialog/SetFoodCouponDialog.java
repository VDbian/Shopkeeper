package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.widget.MySpinner;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class SetFoodCouponDialog extends AppCompatDialog {

    public SetFoodCouponDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {


        private SetFoodCouponDialog dialog;
        private Context context;
        private int theme;
        AppCompatButton oneBtn;

        private String title;
        private String name;


        private OnButtonClick buttonClick;
        private List<FoodBean> list;
        private List<FoodBean> selectFoods;
        private List<MenuTypeEntity> types;
        private List<MenuTypeEntity> selectTypes;

        public void setButtonClick(OnButtonClick buttonClick) {
            this.buttonClick = buttonClick;
        }

        public Builder(Context context, List<FoodBean> foodBeanList, List<MenuTypeEntity> types, int theme) {
            this.context = context;
            this.theme = theme;
            this.list = foodBeanList;
            this.types = types;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public SetFoodCouponDialog creater() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_setfood_coupon_down, null);
            dialog = new SetFoodCouponDialog(context, theme, view);

            oneBtn = (AppCompatButton) view.findViewById(R.id.btn_sure);
            AppCompatButton btnCancel = (AppCompatButton) view.findViewById(R.id.btn_cancel);
            MySpinner spinerFood = (MySpinner) view.findViewById(R.id.tv_name);
            spinerFood.initContent(list);
            spinerFood.setButtonClick(new MySpinner.OnButtonClick() {
                @Override
                public void onSure(List<FoodBean> list) {
                    selectFoods = list;
                    if (selectFoods == null || selectFoods.size() == 0) {
                        spinerFood.setText("请选择商品");
                    } else if (selectFoods.get(0).getProductName().equals("全选")) {
                        spinerFood.setText("全选");
                    } else {
                        String names = "";
                        for (FoodBean bean : selectFoods) {
                            names += bean.getProductName() + ",";
                        }

                        spinerFood.setText(names.substring(0, names.length() - 1));
                    }

                }

                @Override
                public void onSureTypes(List<MenuTypeEntity> typeEntityList) {

                }

            });
            MySpinner spinerFoodType = (MySpinner) view.findViewById(R.id.spiner_food_type);
            spinerFoodType.initTypeContent(types);
            spinerFoodType.setButtonClick(new MySpinner.OnButtonClick() {
                @Override
                public void onSure(List<FoodBean> list) {


                }

                @Override
                public void onSureTypes(List<MenuTypeEntity> typeEntityList) {
                    selectTypes = typeEntityList;
                    if (selectTypes == null || selectTypes.size() == 0) {
                        spinerFoodType.setText("请选择商品类型");
                    } else if (selectTypes.get(0).getProductTypeName().equals("全选")) {
                        spinerFoodType.setText("全选");
                    } else {
                        String names = "";
                        for (MenuTypeEntity bean : selectTypes) {
                            names += bean.getProductTypeName() + ",";
                        }

                        spinerFoodType.setText(names.substring(0, names.length() - 1));
                    }
                }

            });
            TextView tvTitle = (TextView) view.findViewById(R.id.title);


            tvTitle.setText("设置商品（类别）");
            oneBtn.setText("保存内容");
            btnCancel.setText("取消");


            oneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonClick != null) {
                        if (selectFoods != null && selectFoods.size() > 0) {

                        } else {
                            Toasty.warning(context, "请选择商品", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        if (selectTypes != null && selectTypes.size() > 0) {

                        } else {
                            Toasty.warning(context, "请选择商品类型", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        buttonClick.onBtnClick(selectTypes, selectFoods);
                        dialog.dismiss();
                    }
                }
            });

            view.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
                /*Tools.hideSoftKeyboard(context, editText);*/
                dialog.dismiss();
                buttonClick.onCancel();
            });


            return dialog;
        }


        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    public interface OnButtonClick {

        void onBtnClick(List<MenuTypeEntity> typeEntityList, List<FoodBean> foodBeanList);

        void onCancel();
    }
}
