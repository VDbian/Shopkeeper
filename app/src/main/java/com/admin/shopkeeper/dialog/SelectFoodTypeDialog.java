package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.PopFoodTypeAdapter;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.weight.MarginDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class SelectFoodTypeDialog extends AppCompatDialog {

    public SelectFoodTypeDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private SelectFoodTypeDialog dialog;
        private Context context;
        private int theme;
        PopFoodTypeAdapter adapter;

        private String title;
        private List<MenuTypeEntity> datas;

        public List<MenuTypeEntity> getDatas() {
            return datas;
        }

        public void setDatas(List<MenuTypeEntity> datas) {
            this.datas = datas;
        }

        private OnButtonClick buttonClick;

        public void setButtonClick(OnButtonClick buttonClick) {
            this.buttonClick = buttonClick;
        }

        public Builder(Context context, int theme) {
            this.context = context;
            this.theme = theme;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public SelectFoodTypeDialog creater() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_muti, null);
            dialog = new SelectFoodTypeDialog(context, theme, view);

            Button oneBtn = (Button) view.findViewById(R.id.OneBtn);
            oneBtn.setBackgroundResource(R.drawable.selector_dialog_btn2);
            Button okBtn = (Button) view.findViewById(R.id.dialog_ok);
            okBtn.setBackgroundResource(R.drawable.selector_dialog_btn2);
            TextView titletv = (TextView) view.findViewById(R.id.dialog_title);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scrollView);

            titletv.setText(title);

            oneBtn.setOnClickListener(v -> {
                dismiss();
            });

            okBtn.setOnClickListener(v -> {
                if (buttonClick != null) {
                    List<MenuTypeEntity> list = new ArrayList<>();
                    for (MenuTypeEntity food : getDatas()) {
                        if(food.isCheck()){
                            list.add(food);
                        }
                    }
                    buttonClick.onOk(list);
                }
                dismiss();
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new PopFoodTypeAdapter(R.layout.item_muti, getDatas());
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
            recyclerView.setAdapter(adapter);


            adapter.setOnItemClickListener((adapter, view1, position) -> {
                MenuTypeEntity bean = getDatas().get(position);
                if (bean.getProductTypeName().equals("全选")) {
                    boolean check = bean.isCheck();
                    for (MenuTypeEntity food : getDatas()) {
                        food.setCheck(!check);
                    }
                } else {
                    if (getDatas().get(position).isCheck()) {
                        getDatas().get(position).setCheck(false);
                    } else {
                        getDatas().get(position).setCheck(true);
                    }
                }

                adapter.notifyDataSetChanged();
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
        void onOk(List<MenuTypeEntity> list);
    }
}
