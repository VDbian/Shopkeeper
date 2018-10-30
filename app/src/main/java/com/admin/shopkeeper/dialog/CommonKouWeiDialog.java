package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.KouWeiAdapter;
import com.admin.shopkeeper.entity.KouWei;

import com.admin.shopkeeper.weight.MarginDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;


import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class CommonKouWeiDialog extends AppCompatDialog {

    public CommonKouWeiDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {


        private CommonKouWeiDialog dialog;
        private Context context;
        private int theme;
        AppCompatButton oneBtn;
        KouWeiAdapter kouWeiAdapter;

        private String title;
        private String btnStr;
        private List<KouWei> kouWeis;

        public List<KouWei> getKouWeis() {
            return kouWeis;
        }

        public void setKouWeis(List<KouWei> kouWeis) {
            this.kouWeis = kouWeis;
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

        public String getBtnStr() {
            return btnStr;
        }

        public void setBtnStr(String btnStr) {
            this.btnStr = btnStr;
        }


        public CommonKouWeiDialog creater() {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_kouwei, null);
            dialog = new CommonKouWeiDialog(context, theme, view);

            oneBtn = (AppCompatButton) view.findViewById(R.id.OneBtn);
            AppCompatTextView titletv = (AppCompatTextView) view.findViewById(R.id.title);
            titletv.setText(title);
            AppCompatImageButton button = (AppCompatImageButton) view.findViewById(R.id.tipBtn);
            button.setOnClickListener(v -> dismiss());

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scrollView);


            recyclerView.setLayoutManager(new GridLayoutManager(context, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            kouWeiAdapter = new KouWeiAdapter(getKouWeis());
            kouWeiAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
                @Override
                public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                    if (position == 0) {
                        return 3;
                    }
                    return 1;
                }
            });

            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(kouWeiAdapter);
            recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
            recyclerView.setAdapter(kouWeiAdapter);

            kouWeiAdapter.setOnItemClickListener((adapter, view1, position) -> {

                if (position != 0) {
                    KouWei kouWei = kouWeiAdapter.getItem(position);
                    assert kouWei != null;
                    kouWei.setSelected(!kouWei.isSelected());
                    kouWeiAdapter.notifyItemChanged(position, kouWei);
                }

//                changeBtn();
            });


            oneBtn.setText(btnStr);
            oneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (buttonClick != null) {

                        buttonClick.onBtnClick(kouWeiAdapter.getData());

                    }
                }
            });

//            changeBtn();
            return dialog;
        }

        private void changeBtn() {
//
            int c = 0;
            for (KouWei k : kouWeiAdapter.getData()) {
                if (k.isSelected()) {
                    c++;
                }
            }
            Timber.d(c+"11111111111111111");
            oneBtn.setEnabled(c > 0);
        }

        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    public interface OnButtonClick {

        void onBtnClick(List<KouWei> kouWeis);
    }
}
