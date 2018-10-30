package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.CouponLineAdapter;
import com.admin.shopkeeper.adapter.CouponLineChoiceAdapter;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.weight.MarginDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 线下券dialog
 */

public class CouponLineDialog extends AppCompatDialog {


    public CouponLineDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {

        private CouponLineDialog dialog;
        private Context context;
        private int theme;
        AppCompatButton oneBtn;

        private String title;
        private String btnStr;
        private double max;
        private List<CouponLineDownBean> reasons;
        private CouponLineAdapter adapter;
        private CouponLineChoiceAdapter adapter1;
        List<CouponLineDownBean> list = new ArrayList<>();

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public List<CouponLineDownBean> getReasons() {
            return reasons;
        }

        public void setReasons(List<CouponLineDownBean> reasons) {
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

        public String getBtnStr() {
            return btnStr;
        }

        public void setBtnStr(String btnStr) {
            this.btnStr = btnStr;
        }

        String hint;

        public void setHint(String hint) {
            this.hint = hint;
        }

        String text;

        public void setText(String text) {
            this.text = text;
        }


        public CouponLineDialog creater() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_coupon_line, null);
            dialog = new CouponLineDialog(context, theme, view);

            oneBtn = (AppCompatButton) view.findViewById(R.id.OneBtn);
            AppCompatTextView titletv = (AppCompatTextView) view.findViewById(R.id.title);
            titletv.setText(title);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scrollView);
            RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.scrollView1);
            AppCompatButton btnCancel = (AppCompatButton) view.findViewById(R.id.btn_cancel);
            btnCancel.setText("取消");
            oneBtn.setText(btnStr);
            oneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonClick != null) {

                        if (list == null) {
                            Toasty.warning(context, "请选择线下券", Toast.LENGTH_SHORT, true).show();
                            return;
                        }

                        buttonClick.onBtnClick(list);
                    }
                }
            });
            btnCancel.setOnClickListener(v -> {
                buttonClick.onCancel();
                dismiss();
            });


            recyclerView.setLayoutManager(new GridLayoutManager(context, 2) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }
            });

            adapter = new CouponLineAdapter(R.layout.item_da_zhe_dialog, getReasons());
            recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
            recyclerView.setAdapter(adapter);

            recyclerView1.setLayoutManager(new LinearLayoutManager(context));
            recyclerView1.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                    .marginResId(R.dimen._1sdp, R.dimen._1sdp)
                    .color(context.getResources().getColor(R.color.item_line_color))
                    .build());
            adapter1 = new CouponLineChoiceAdapter(R.layout.item_coupon_line_choice, list);
            recyclerView1.setAdapter(adapter1);
            adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.item_delete) {
                        list.remove(position);
                        adapter1.notifyDataSetChanged();
                    }
                }
            });
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
//                    if (buttonClick != null) {
//                        buttonClick.onItemClick(adapter.getData().get(position));
//                    }
                    List<CouponLineDownBean> data = adapter.getData();
                    /*for (int i = 0; i > data.size(); i++) {
                        data.get(i).setSelected(false);
                    }*/
                    CouponLineDownBean bean = adapter.getItem(position);
                    int size = 0;
                    for (CouponLineDownBean bean1 : list) {
                        if (bean1.getGuid().equals(bean.getGuid())) {
                            size++;
                        }
                    }
                    if(size >= bean.getCounts()){
                        Toasty.warning(context, bean.getName()+"已经使用完毕", Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    if (size >= bean.getMaxUseCount()) {
                        Toasty.warning(context, bean.getName()+"超过单次最大使用量", Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    list.add(bean);
//                    data.get(position).setSelected(true);
                    adapter.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();
                }
            });

            oneBtn.setText(btnStr);
            return dialog;
        }


        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    public interface OnButtonClick {

        void onBtnClick(List<CouponLineDownBean> list);

        void onCancel();
    }
}
