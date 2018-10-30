package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.ChainDialogAdapter;
import com.admin.shopkeeper.adapter.TimeTypeAdapter;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.TimeTypeBean;
import com.admin.shopkeeper.weight.MarginDecoration;

import java.util.List;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class TimeTypeDialog extends AppCompatDialog {

    public TimeTypeDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private TimeTypeDialog dialog;
        private Context context;
        private int theme;
        TimeTypeAdapter adapter;

        boolean isSingleSelect = false;

        private String title;
        private List<TimeTypeBean> reasons;
        String select;

        public String getSelect() {
            return select;
        }

        public void setSelect(String select) {
            this.select = select;
        }

        public void setSingleSelect(boolean singleSelect) {
            isSingleSelect = singleSelect;
        }

        public List<TimeTypeBean> getReasons() {
            return reasons;
        }

        public void setReasons(List<TimeTypeBean> reasons) {
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

        public TimeTypeDialog creater() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_timetype, null);
            dialog = new TimeTypeDialog(context, theme, view);

            Button oneBtn = (Button) view.findViewById(R.id.OneBtn);
            Button okBtn = (Button) view.findViewById(R.id.dialog_ok);
            TextView titletv = (TextView) view.findViewById(R.id.dialog_title);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scrollView);

            titletv.setText(title);

            if (!TextUtils.isEmpty(select)) {
                for (TimeTypeBean mutiBean : getReasons()) {
                    if (select.equals(mutiBean.getName())) {
                        mutiBean.setSelect(true);
                    } else {
                        mutiBean.setSelect(false);
                    }
                }
            }

            oneBtn.setOnClickListener(v -> {
                dismiss();
            });
            okBtn.setOnClickListener(v -> {
                if (buttonClick != null) {
                    for (int i = 0; i < getReasons().size(); i++) {
                        if (getReasons().get(i).isSelect()) {
                            buttonClick.onOk(getReasons().get(i).getName());
                        }
                    }
                }
                dismiss();
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new TimeTypeAdapter(R.layout.item_muti, getReasons());
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener((adapter, view1, position) -> {
                Log.i("ttt", "--" + position);
                if (getReasons().get(position).isSelect()) {
                    getReasons().get(position).setSelect(false);
                } else {
                    for (TimeTypeBean chainBean : getReasons()) {
                        chainBean.setSelect(false);
                    }
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
        void onOk(String text);
    }
}
