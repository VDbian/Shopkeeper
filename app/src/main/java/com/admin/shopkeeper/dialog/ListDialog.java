package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.Toast;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.DaZhaAdapter;
import com.admin.shopkeeper.adapter.ListDialogAdapter;
import com.admin.shopkeeper.entity.DaZheEntity;
import com.admin.shopkeeper.weight.MarginDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class ListDialog extends AppCompatDialog {

    public ListDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private ListDialog dialog;
        private Context context;
        private int theme;
        ListDialogAdapter daZhaAdapter;

        private String title;
        private List<String> reasons;

        public List<String> getReasons() {
            return reasons;
        }

        public void setReasons(List<String> reasons) {
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


        public ListDialog creater() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_list, null);
            dialog = new ListDialog(context, theme, view);

            Button oneBtn = (Button) view.findViewById(R.id.OneBtn);
            TextView titletv = (TextView) view.findViewById(R.id.dialog_title);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scrollView);

            titletv.setText(title);
            oneBtn.setOnClickListener(v -> {
                dismiss();
            });

            //recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            daZhaAdapter = new ListDialogAdapter(R.layout.item_da_zhe_dialog, getReasons());
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
            recyclerView.setAdapter(daZhaAdapter);

            daZhaAdapter.setOnItemClickListener((adapter, view1, position) -> {
                if (buttonClick != null) {
                    buttonClick.onItemClick(position, getReasons().get(position));
                }
                dismiss();
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
        void onItemClick(int i, String str);
    }
}
