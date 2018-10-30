package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.FindFoodAdapter;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.entity.FindFoodCouponDownBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.setOrLookFood.SetOrLookFoodPresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class FindFoodCouponDialog extends AppCompatDialog {

    public FindFoodCouponDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }


    public static class Builder {

        private FindFoodCouponDialog dialog;
        private Context context;
        private int theme;
        AppCompatButton oneBtn;

        private String title;
        private String name;


        private OnRefreshListener onRefreshListener;
        private List<FindFoodCouponDownBean> list = new ArrayList<>();
        public FindFoodAdapter adapter;
        SetOrLookFoodPresenter mPresenter;
        public SwipeRefreshLayout refreshLayout;
        public RecyclerView recyclerView;

        CouponLineDownBean bean;

        boolean isSearch = false;

        public CouponLineDownBean getBean() {
            return bean;
        }

        public void setBean(CouponLineDownBean bean) {
            this.bean = bean;
        }

        public void setOnRefreshListener(OnRefreshListener listener) {
            this.onRefreshListener = listener;
        }

        public Builder(Context context, int theme, SetOrLookFoodPresenter presenter) {
            this.context = context;
            this.theme = theme;
            this.mPresenter = presenter;
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

        public void setDatas(List<FindFoodCouponDownBean> data) {
            this.list = data;
            adapter.setNewData(list);
            refreshLayout.setRefreshing(false);
            if (data.size() % 20 == 0) {
                adapter.loadMoreComplete();
            } else {
                adapter.loadMoreEnd();
            }
        }

        public void errorRefresh() {
            refreshLayout.setRefreshing(false);
            adapter.loadMoreFail();
        }

        public FindFoodCouponDialog creater() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_look_food_coupon_down, null);
            dialog = new FindFoodCouponDialog(context, theme, view);

            TextView tvTitle = (TextView) view.findViewById(R.id.title);
            TextView tvFoodName = (TextView) view.findViewById(R.id.food_name);

            tvTitle.setText("查看设置商品");
            tvFoodName.setText("商品名称");

            AppCompatEditText editText = (AppCompatEditText) view.findViewById(R.id.et_food_search);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_clear);

            refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
            refreshLayout.setOnRefreshListener(() -> {
                if(isSearch){
                    return;
                }
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
            });
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                    .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                    .color(context.getResources().getColor(R.color.item_line_color))
                    .build());
            adapter = new FindFoodAdapter(R.layout.item_find_food, context);
            recyclerView.setAdapter(adapter);
            adapter.setNewData(list);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s.toString().trim())) {
                        isSearch = false;
                        adapter.setEnableLoadMore(true);
                        iv.setVisibility(View.INVISIBLE);
                        adapter.setNewData(list);
                    } else {
                        isSearch = true;
                        adapter.setEnableLoadMore(false);
                        iv.setVisibility(View.VISIBLE);
                        searchFood(s.toString().trim());
                    }
                }
            });
            editText.clearFocus();

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("");
                }
            });

            adapter.setOnLoadMoreListener(() -> {
                if(isSearch){
                    return;
                }
                if (onRefreshListener != null) {
                    onRefreshListener.onLoadMore();
                }
            }, recyclerView);

            return dialog;
        }

        private void searchFood(String str) {
            List<FindFoodCouponDownBean> FFCDBlist = new ArrayList<>();
            for (FindFoodCouponDownBean bean : list) {
                if (bean.getProductName().contains(str)) {
                    FFCDBlist.add(bean);
                }
            }
            adapter.setNewData(FFCDBlist);
            refreshLayout.setRefreshing(false);
        }

        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    public interface OnRefreshListener {

        void onRefresh();

        void onLoadMore();
    }

}
