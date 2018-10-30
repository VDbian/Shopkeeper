package com.admin.shopkeeper.ui.fragment.order;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.MenuDropDownAdapter;
import com.admin.shopkeeper.adapter.OrderAdapter;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.TPayType;
import com.admin.shopkeeper.ui.activity.orderDetail.OrderDetailActivity;
import com.admin.shopkeeper.weight.DropDownMenu;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends BaseFragment<OrderPresenter> implements IOrderView, BaseQuickAdapter.RequestLoadMoreListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int index = 1;
    private int size = 10;

    private PtrFrameLayout mFrameLayout;


    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    private OrderAdapter mAdapter;


    private MenuDropDownAdapter statusAdapter;
    private MenuDropDownAdapter typeAdapter;


    private List<View> popupViews = new ArrayList<>();

    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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
        return R.layout.fragment_order;
    }

    @Override
    protected void initPresenter() {
        presenter = new OrderPresenter(getActivity(), this);
        presenter.init();

    }


    @Override
    public void initView() {

        EventBus.getDefault().register(this);
        initMenu();

    }

    private void initMenu() {

        String[] status = getResources().getStringArray(R.array.orderStatus);
        String[] type = getResources().getStringArray(R.array.orderType);
        String headers[] = {"订单状态", "订单类型"};

        ListView statusView = new ListView(getActivity());
        statusAdapter = new MenuDropDownAdapter(getActivity(), Arrays.asList(status));
        statusView.setDividerHeight(0);
        statusView.setAdapter(statusAdapter);

        ListView typeView = new ListView(getActivity());
        typeAdapter = new MenuDropDownAdapter(getActivity(), Arrays.asList(type));
        typeView.setDividerHeight(0);
        typeView.setAdapter(typeAdapter);

        popupViews.add(statusView);
        popupViews.add(typeView);


        //add item click event
        statusView.setOnItemClickListener((parent, view, position, id) -> {

            dropDownMenu.setTabText(position == 0 ? headers[0] : status[position]);
            dropDownMenu.closeMenu();
            if (statusAdapter.getCheckItemPosition() != position) {
                statusAdapter.setCheckItem(position);
                mFrameLayout.postDelayed(() -> mFrameLayout.autoRefresh(), 100);
            }

        });


        typeView.setOnItemClickListener((parent, view, position, id) -> {

            dropDownMenu.setTabText(position == 0 ? headers[1] : type[position]);
            dropDownMenu.closeMenu();
            if (typeAdapter.getCheckItemPosition() != position) {
                typeAdapter.setCheckItem(position);
                mFrameLayout.postDelayed(() -> mFrameLayout.autoRefresh(), 100);
            }

        });


        mFrameLayout = (PtrFrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.content_order, dropDownMenu, false);

        initPTRLayout();

        //init dropdownview
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mFrameLayout);

        RecyclerView mRecyclerView = (RecyclerView) mFrameLayout.findViewById(R.id.recyclerView);

        mAdapter = new OrderAdapter(R.layout.item_order);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity()).color(Color.parseColor("#F8F8F8")).sizeResId(R.dimen._10sdp).build());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            presenter.getOrderDetail(mAdapter.getItem(position), position);
        });

        mFrameLayout.postDelayed(() -> mFrameLayout.autoRefresh(), 100);
    }


    private void initPTRLayout() {
        MaterialHeader header = new MaterialHeader(getContext());
        int c[] = {ContextCompat.getColor(getContext(), R.color.colorPrimary)};
        header.setColorSchemeColors(c);
        header.setPadding(0, PtrLocalDisplay.dp2px(20), 0, PtrLocalDisplay.dp2px(20));
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        mFrameLayout.setDurationToCloseHeader(1000);
        mFrameLayout.setHeaderView(header);
        mFrameLayout.addPtrUIHandler(header);
        mFrameLayout.setPinContent(true);//设置为true时content的内容位置将不会改变

        mFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                index = 1;
                presenter.getOrderList(statusAdapter.getCheckItemPosition(), typeAdapter.getCheckItemPosition(), index, size);
            }
        });
    }

    public boolean menuIsVisible() {
        return dropDownMenu != null && dropDownMenu.isShowing();
    }


    public void closeMenu() {
        if (dropDownMenu != null && dropDownMenu.isShowing()) {
            dropDownMenu.closeMenu();
        }

    }

    @Override
    public void warning(String message) {
        refreshComplete();
        Toasty.warning(getActivity(), message, Toast.LENGTH_SHORT, true).show();
    }

    private void refreshComplete() {

        if (mFrameLayout != null && mFrameLayout.isRefreshing()) {
            mFrameLayout.refreshComplete();
        }
    }

    @Override
    public void error(String message) {
        if (index == 1) {
            refreshComplete();
            Toasty.error(getActivity(), message, Toast.LENGTH_SHORT, true).show();
        } else {
            mAdapter.loadMoreFail();
        }

    }

    @Override
    public void success(List<Order> orders) {
        refreshComplete();

        if (index == 1) {
            mAdapter.setNewData(orders);
            index++;
            mAdapter.setEnableLoadMore(true);
        } else {

            if (orders.size() > 0) {
                mAdapter.addData(new ArrayList<>(orders));
                index++;
                mAdapter.loadMoreComplete();
            } else {
                mAdapter.loadMoreEnd();
            }


        }

    }

    @Override
    public void toDetail(Order order, List<OrderDetailFood> detailFoods, List<TPayType> tPayTypes, int position) {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra(Config.PARAM1, order);
        intent.putExtra(Config.PARAM2, (Serializable) detailFoods);
        intent.putExtra(Config.PARAM3, position);
        intent.putExtra(Config.PARAM4, OrderDetailActivity.P1);
        intent.putExtra(Config.PARAM5, (Serializable) tPayTypes);
        getActivity().startActivity(intent);
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getOrderList(statusAdapter.getCheckItemPosition(), typeAdapter.getCheckItemPosition(), index, size);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent event) {
        int i = event.getType();
        if (i == MsgEvent.OrderList) {

            int position = (int) event.getOther();
            Order o = (Order) event.getData();
            mAdapter.getData().set(position, o);

            mAdapter.notifyItemChanged(position, o);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
