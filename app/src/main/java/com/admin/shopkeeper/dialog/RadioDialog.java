package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.RadioAdapter;
import com.admin.shopkeeper.entity.OrderfoodEntity;
import com.admin.shopkeeper.entity.RadioEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class RadioDialog extends AppCompatDialog {

    public RadioDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private RadioDialog dialog;
        private Context context;
        private int theme;

        private String title;
        private String btnStr;
        private List<RadioEntity> radioEntities;

        private OnButtonClick buttonClick;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBtnStr() {
            return btnStr;
        }

        public void setBtnStr(String btnStr) {
            this.btnStr = btnStr;
        }

        public List<RadioEntity> getRadioEntities() {
            return radioEntities;
        }

        public void setRadioEntities(List<RadioEntity> radioEntities) {
            this.radioEntities = radioEntities;
        }

        public void setButtonClick(OnButtonClick buttonClick) {
            this.buttonClick = buttonClick;
        }

        public Builder(Context context, int theme) {
            this.context = context;
            this.theme = theme;
        }

        public RadioDialog creater() {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_radio, null);
            dialog = new RadioDialog(context, theme, view);


            AppCompatTextView titletv = (AppCompatTextView) view.findViewById(R.id.title);
            titletv.setText(title);
            AppCompatImageButton button = (AppCompatImageButton) view.findViewById(R.id.tipBtn);
            button.setOnClickListener(v -> dismiss());

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scrollView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            RadioAdapter adapter = new RadioAdapter(R.layout.item_radio, radioEntities);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener((adapter1, view1, position) -> {
                RadioEntity entity = (RadioEntity) adapter1.getItem(position);
                if (!entity.isSelected()) {
                    for (int i = 0; i < adapter1.getData().size(); i++) {
                        RadioEntity e = (RadioEntity) adapter1.getItem(i);
                        e.setSelected(false);
                    }
                    entity.setSelected(true);
                }
                adapter.notifyDataSetChanged();
            });


            AppCompatButton oneBtn = (AppCompatButton) view.findViewById(R.id.OneBtn);
            oneBtn.setText(btnStr);
            oneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (buttonClick != null) {
                        int p = 0;
                        for (int i = 0; i < adapter.getData().size(); i++) {
                            RadioEntity e = (RadioEntity) adapter.getItem(i);

                            if (e.isSelected()) {
                                p = i;
                                break;
                            }
                        }
                        buttonClick.onBtnClick(p);
                    }
                }
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

        void onBtnClick(int position);
    }

}
