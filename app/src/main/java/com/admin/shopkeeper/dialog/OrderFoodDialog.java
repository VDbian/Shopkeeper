package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.KouWeiAdapter;
import com.admin.shopkeeper.adapter.MaterialAdapter;
import com.admin.shopkeeper.adapter.SpecAdapter;
import com.admin.shopkeeper.entity.KouWei;
import com.admin.shopkeeper.entity.OrderfoodEntity;
import com.admin.shopkeeper.entity.Season;
import com.admin.shopkeeper.entity.Spec;
import com.admin.shopkeeper.weight.MarginDecoration;
import com.admin.shopkeeper.weight.SpacesItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;


/**
 * Created by admin on 2017/6/9.
 */

public class OrderFoodDialog extends AppCompatDialog {

    private OrderFoodDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private OrderFoodDialog dialog;
        private Context context;
        private int theme;

        AppCompatImageButton reduce;
        AppCompatTextView numberText;
        AppCompatButton button;
        AppCompatButton leftBtn;
        AppCompatButton rightBtn;
        private OnButtonClick buttonClick;


        public void setButtonClick(OnButtonClick buttonClick) {
            this.buttonClick = buttonClick;
        }

        public OrderfoodEntity getEntity() {
            return entity;
        }

        public void setEntity(OrderfoodEntity entity) {
            this.entity = entity;
        }

        private OrderfoodEntity entity;

        public Builder(Context context, int theme) {
            this.context = context;
            this.theme = theme;
        }


        public OrderFoodDialog creater() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_order_food, null);
            dialog = new OrderFoodDialog(context, theme, view);

            if (entity == null) {
                return dialog;
            }

            leftBtn = (AppCompatButton) view.findViewById(R.id.leftBtn);
            rightBtn = (AppCompatButton) view.findViewById(R.id.rightBtn);
            button = (AppCompatButton) view.findViewById(R.id.OneBtn);

            AppCompatImageButton tipBtn = (AppCompatImageButton) view.findViewById(R.id.tipBtn);
            tipBtn.setOnClickListener(v -> {
                dismiss();
            });
            AppCompatTextView title = (AppCompatTextView) view.findViewById(R.id.foodName);

            if (entity.isType()) {
                title.setText(entity.getPackageName());
            } else {
                title.setText(entity.getTitle());
            }

            AppCompatTextView unitPrice = (AppCompatTextView) view.findViewById(R.id.unitPriceText);

            //单价
            unitPrice.setText(String.format(context.getString(R.string.string_unit_price), entity.getPrice(), entity.getUnit()));
            ConstraintLayout numLayout = (ConstraintLayout) view.findViewById(R.id.numberLayout);

            if (entity.isNumLayout()) {
                reduce = (AppCompatImageButton) view.findViewById(R.id.reduce);//减号
                AppCompatImageButton add = (AppCompatImageButton) view.findViewById(R.id.add);//加号
                numberText = (AppCompatTextView) view.findViewById(R.id.numberText);
                numberText.setText(String.valueOf(entity.getNumber()));
                showReduce(entity.getNumber());
                reduce.setOnClickListener(v -> {
                    int num = entity.getNumber();
                    entity.setNumber(num - 1);
                    numberText.setText(String.valueOf(entity.getNumber()));
                    showReduce(entity.getNumber());
                    btnEnable();
                });
                add.setOnClickListener(v -> {
                    int num = entity.getNumber();
                    if (num < 99) {
                        entity.setNumber(num + 1);
                    } else {
                        entity.setNumber(99);
                    }
                    showReduce(entity.getNumber());
                    numberText.setText(String.valueOf(entity.getNumber()));
                    btnEnable();
                });
            }
            ConstraintLayout weightLayout = (ConstraintLayout) view.findViewById(R.id.weightLayout);//称重
            AppCompatEditText weightText = (AppCompatEditText) view.findViewById(R.id.weightText);//称重

            if (entity.isShowWeight()) {
                numLayout.setVisibility(View.GONE);
                weightText.setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        weightText.setText("");
                    }
                });

                btnEnable();
                weightText.setText(String.format(context.getString(R.string.string_weight_format), entity.getWeight()));
                weightText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().contains(".")) {
                            if (s.length() - 1 - s.toString().indexOf(".") > 3) {
                                s = s.toString().subSequence(0,
                                        s.toString().indexOf(".") + 4);
                                weightText.setText(s);
                                weightText.setSelection(s.length());
                            }
                        }
                        if (s.toString().trim().equals(".")) {
                            s = "0" + s;
                            weightText.setText(s);
                            weightText.setSelection(2);
                        }
                        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                            if (!s.toString().substring(1, 2).equals(".")) {
                                weightText.setText(s.subSequence(0, 1));
                                weightText.setSelection(1);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(s) || s.toString().endsWith(".")
                                || (Double.valueOf(s.toString()) <= 0)) {
                            entity.setWeight(0);
                        } else {
                            entity.setWeight((Double.valueOf(s.toString())));
                        }
                        btnEnable();
                    }

                });
            } else {
                weightLayout.setVisibility(View.GONE);
            }

            ConstraintLayout specLayout = (ConstraintLayout) view.findViewById(R.id.specLayout);//规格
            if (entity.getSpecs() != null && entity.getSpecs().size() > 0) {
                if (!entity.isEdit()) {
                    for (int i = 0; i < entity.getSpecs().size(); i++) {

                        if (i == 0 && entity.getSpecs().get(i).isSelected()) {
                            break;
                        } else if (entity.getSpecs().get(i).isSelected()) {
                            entity.getSpecs().get(0).setSelected(true);
                            entity.getSpecs().get(i).setSelected(false);
                            break;
                        }
                    }
                    entity.getSpecs().get(0).setSelected(true);
                    entity.setPrice(entity.getSpecs().get(0).getPrice());
                }

                unitPrice.setText(String.format(context.getString(R.string.string_unit_price),
                        entity.getPrice(), entity.getUnit()));
                RecyclerView specRecyclerView = (RecyclerView) view.findViewById(R.id.specRecyclerView);

                SpecAdapter sAdapter = new SpecAdapter(R.layout.item_order_food_dialog);
                specRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
                specRecyclerView.setAdapter(sAdapter);
                specRecyclerView.addItemDecoration(new SpacesItemDecoration(3,
                        context.getResources().getDimensionPixelSize(R.dimen._20sdp), false));

                sAdapter.setNewData(entity.getSpecs());


                sAdapter.setOnItemClickListener((adapter, view12, position) -> {

                    Spec type = sAdapter.getItem(position);
                    assert type != null;
                    if (!type.isSelected()) {
                        for (Spec m : sAdapter.getData()) {
                            m.setSelected(false);
                        }
                        type.setSelected(true);
                        entity.setPrice(type.getPrice());
                        unitPrice.setText(String.format(context.getString(R.string.string_unit_price),
                                entity.getPrice(), entity.getUnit()));
                    }
                    sAdapter.notifyDataSetChanged();
                });

            } else {
                specLayout.setVisibility(View.GONE);
            }

            ConstraintLayout jialiaoLayout = (ConstraintLayout) view.findViewById(R.id.jialiaoLayout);//加料
            if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                if(entity.isEdit()){
                    for (int i = 0; i < entity.getSeasons().size(); i++) {
                        if (entity.getSeasons().get(i).isSelected()) {
                            entity.getSeasons().get(i).setSelected(true);
                        }
                    }
                }else{
                    for (int i = 0; i < entity.getSeasons().size(); i++) {
                        if (entity.getSeasons().get(i).isSelected()) {
                            entity.getSeasons().get(i).setSelected(false);
                        }
                    }
                }

                RecyclerView jialiaoRecyclerView = (RecyclerView) view.findViewById(R.id.jialiaoRecyclerView);
                MaterialAdapter jAdapter = new MaterialAdapter(entity.getSeasons());
                jAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if(view.getId() == R.id.reduce){

                        }
                    }
                });
                jialiaoRecyclerView.setHasFixedSize(true);
//                jialiaoRecyclerView.setLayoutManager(new GridLayoutManager(context, 3) {
//
//                    @Override
//                    public boolean canScrollVertically() {
//                        return false;
//                    }
//                });
                jialiaoRecyclerView.setLayoutManager(new LinearLayoutManager(context){
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                jialiaoRecyclerView.setAdapter(jAdapter);
                jialiaoRecyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
                jAdapter.setOnItemClickListener((adapter, view1, position) -> {
                   /* Season season = jAdapter.getItem(position);
                    assert season != null;
                    if (season.isSelected()) {
                        season.setSelected(false);
                    } else {
                        for (Season s : entity.getSeasons()) {
                            s.setSelected(false);
                        }
                        season.setSelected(true);
                    }*/

                        Season season = jAdapter.getItem(position);
                        assert season != null;
                    if(season.isSelected()){
                        int seasonNum = season.getCount()+1;
                        season.setCount(seasonNum);
                        season.setSelected(true);
                    }else {
                        season.setCount(1);
                        season.setSelected(true);
                    }
                        jAdapter.notifyItemChanged(position, season);
                    //season.setSelected(!season.isSelected());
//                    jAdapter.notifyDataSetChanged();
                });
            } else {
                jialiaoLayout.setVisibility(View.GONE);
            }

            ConstraintLayout kouweiLayout = (ConstraintLayout) view.findViewById(R.id.kouweiLayout);//口味
            if (entity.getKouWeis() != null && entity.getKouWeis().size() > 0) {
                if (!entity.isEdit()) {
                    for (int i = 0; i < entity.getKouWeis().size(); i++) {
                        if (entity.getKouWeis().get(i).isSelected()) {
                            entity.getKouWeis().get(i).setSelected(false);
                        }
                    }
                }

                RecyclerView kouweiRecyclerView = (RecyclerView) view.findViewById(R.id.kouweiRecyclerView);
                KouWeiAdapter kAdapter = new KouWeiAdapter(entity.getKouWeis());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };

                kAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                        if (position == 0) {
                            return 3;
                        }
                        return 1;
                    }
                });


                kouweiRecyclerView.setHasFixedSize(true);
                kouweiRecyclerView.setLayoutManager(gridLayoutManager);
                kouweiRecyclerView.setAdapter(kAdapter);
                kouweiRecyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
                kAdapter.setOnItemClickListener((adapter, view1, position) -> {
                    if (position != 0) {
                        KouWei kouWei = kAdapter.getItem(position);
                        assert kouWei != null;
                        kouWei.setSelected(!kouWei.isSelected());
                        kAdapter.notifyItemChanged(position, kouWei);
                    }

                });
            } else {
                kouweiLayout.setVisibility(View.GONE);
            }

            if (entity.isMoreBtn()) {//是否按钮组

                button.setVisibility(View.GONE);
                leftBtn.setText(entity.getLeftStr());
                rightBtn.setText(entity.getRightStr());

                leftBtn.setOnClickListener(v -> {
                    if (buttonClick != null) {
                        buttonClick.onLeftBtnClick(entity);
                    }
                });

                rightBtn.setOnClickListener(v -> {
                    if (buttonClick != null) {
                        buttonClick.onRightBtnClick(entity);
                    }
                });

            } else {
                leftBtn.setVisibility(View.GONE);
                rightBtn.setVisibility(View.GONE);
                if (TextUtils.isEmpty(entity.getBtnStr())) {
                    button.setVisibility(View.GONE);
                } else {
                    button.setText(entity.getBtnStr());

                    button.setOnClickListener(v -> {

                        if (buttonClick != null) {
                            buttonClick.onBtnClick(entity);
                        }
                    });
                }
            }


            return dialog;

        }

        private void btnEnable() {
            if (entity.isMoreBtn()) {
                if (entity.isShowWeight()) {
                    button.setEnabled(entity.getWeight() > 0);
                } else if (entity.isNumLayout()) {
                    button.setEnabled(entity.getNumber() > 0);
                }
            } else {
                if (entity.isShowWeight()) {
                    button.setEnabled(entity.getWeight() > 0);
                } else if (entity.isNumLayout()) {
                    button.setEnabled(entity.getNumber() > 0);
                }
            }
        }

        private void showReduce(int number) {
            if (number == 0) {
                reduce.setVisibility(View.GONE);
                numberText.setVisibility(View.GONE);
            } else {
                if (reduce.isShown() || numberText.isShown()) {
                    return;
                }
                reduce.setVisibility(View.VISIBLE);
                numberText.setVisibility(View.VISIBLE);
            }
        }

        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        }

    }

    public interface OnButtonClick {


        void onLeftBtnClick(OrderfoodEntity entity);

        void onRightBtnClick(OrderfoodEntity entity);

        void onBtnClick(OrderfoodEntity entity);
    }


}
