package com.admin.shopkeeper.ui.fragment.message;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.OrderAdapter;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.TPayType;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends BaseFragment<MessagePresenter> implements IMessageView, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private OrderAdapter adapter;

    private int index = 1;
    private int size = 10;

    @BindView(R.id.ptrLayout)
    PtrFrameLayout ptrLayout;

//    private MessageAdapter mAdapter;
//    private List<Message> mList;


   /* @OnClick(R.id.textView)
   void onClick(){
        Intent intent=new Intent(getActivity(), MessageDetailActivity.class);
        startActivity(intent);
    }*/

    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initPresenter() {
        presenter = new MessagePresenter(getActivity(), this);
        presenter.init();
    }

    @Override
    public void initView() {


        initRecyclerView();
        initPTRLayout();

    }

    private void refreshComplete() {

        if (ptrLayout != null && ptrLayout.isRefreshing()) {
            ptrLayout.refreshComplete();
        }
    }

    @Override
    public void success(List<Order> orders) {
        refreshComplete();

        if (index == 1) {

            adapter.setNewData(orders);
            index++;
            adapter.setEnableLoadMore(true);
        } else {

            if (orders.size() > 0) {
                adapter.addData(new ArrayList<>(orders));
                index++;
                adapter.loadMoreComplete();
            } else {
                adapter.loadMoreEnd();
            }


        }
    }

    @Override
    public void warning(String message) {

        refreshComplete();
        Toasty.warning(getActivity(), message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void error(String message) {
        refreshComplete();
        if (index == 1) {
            refreshComplete();
            Toasty.error(getActivity(), message, Toast.LENGTH_SHORT, true).show();
        } else {
            adapter.loadMoreFail();
        }
    }

    @Override
    public void nodata() {
        refreshComplete();
        adapter.loadMoreEnd();
    }

    @Override
    public void toDetail(Order item, List<OrderDetailFood> orderDetailFoods, List<TPayType> tPayTypes , int position) {

    }

    private void initRecyclerView() {
        adapter = new OrderAdapter(R.layout.item_order);
        adapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).color(Color.parseColor("#F8F8F8")).sizeResId(R.dimen._1sdp).build());
        mRecyclerView.setAdapter(adapter);
    }

    private void initPTRLayout() {
        MaterialHeader header = new MaterialHeader(getContext());
        int c[] = {ContextCompat.getColor(getContext(), R.color.colorPrimary)};
        header.setColorSchemeColors(c);
        header.setPadding(0, PtrLocalDisplay.dp2px(20), 0, PtrLocalDisplay.dp2px(20));
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        ptrLayout.setDurationToCloseHeader(1000);
        ptrLayout.setHeaderView(header);
        ptrLayout.addPtrUIHandler(header);
        ptrLayout.setPinContent(true);//设置为true时content的内容位置将不会改变

        ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                index = 1;
                presenter.getMessage(index, size);
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getMessage(index, size);
    }
}
