package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.ShopDialogAdapter;
import com.admin.shopkeeper.entity.ShopBean;
import com.admin.shopkeeper.weight.MarginDecoration;

import java.util.List;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class ShopSelectDialog extends AppCompatDialog {

    public ShopSelectDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private ShopSelectDialog dialog;
        private Context context;
        private int theme;
        ShopDialogAdapter adapter;

        private String title;
        private List<ShopBean> reasons;

        public List<ShopBean> getReasons() {
            return reasons;
        }

        public void setReasons(List<ShopBean> reasons) {
            this.reasons = reasons;
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

        public ShopSelectDialog creater() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_muti, null);
            dialog = new ShopSelectDialog(context, theme, view);

            Button oneBtn = (Button) view.findViewById(R.id.OneBtn);
            Button okBtn = (Button) view.findViewById(R.id.dialog_ok);
            TextView titletv = (TextView) view.findViewById(R.id.dialog_title);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scrollView);

            titletv.setText(title);
            oneBtn.setOnClickListener(v -> {
                dismiss();
            });

            okBtn.setOnClickListener(v -> {
                if (buttonClick != null) {
                    String result = "";
                    String resultStr = "";

                    for (int i = 0; i < getReasons().size(); i++) {
                        if (getReasons().get(i).isSelect()) {
                            result += getReasons().get(i).getGuid() + ",";
                            resultStr += getReasons().get(i).getName() + ",";
                        }
                    }
                    if (!TextUtils.isEmpty(result)) {
                        result = result.substring(0, result.length() - 1);
                        resultStr = resultStr.substring(0, resultStr.length() - 1);
                    }
                    buttonClick.onOk(resultStr, result);
                }
                dismiss();
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new ShopDialogAdapter(R.layout.item_muti, getReasons());
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener((adapter, view1, position) -> {
                if (getReasons().get(position).isSelect()) {
                    getReasons().get(position).setSelect(false);
                } else {
                    getReasons().get(position).setSelect(true);
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
        void onOk(String text, String value);
    }
}
