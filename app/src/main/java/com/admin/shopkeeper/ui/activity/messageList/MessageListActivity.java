package com.admin.shopkeeper.ui.activity.messageList;

import android.content.Intent;
import android.graphics.Color;

import android.support.v4.content.ContextCompat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.OrderAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.TPayType;
import com.admin.shopkeeper.ui.activity.orderDetail.OrderDetailActivity;
import com.admin.shopkeeper.ui.fragment.message.IMessageView;
import com.admin.shopkeeper.ui.fragment.message.MessagePresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class MessageListActivity extends BaseActivity<MessagePresenter> implements IMessageView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private OrderAdapter adapter;

    private int index = 1;
    private int size = 10;

    @BindView(R.id.ptrLayout)
    PtrFrameLayout ptrLayout;

    @Override
    protected void initPresenter() {
        presenter = new MessagePresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("消息");
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initRecyclerView();
        initPTRLayout();
        ptrLayout.postDelayed(() -> ptrLayout.autoRefresh(), 100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
            if(orders.size() == 0){
                showToast("消息列表数据为空");
            }
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
        Toasty.warning(this, message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void error(String message) {
        refreshComplete();
        if (index == 1) {
            refreshComplete();
            Toasty.error(this, message, Toast.LENGTH_SHORT, true).show();
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
    public void toDetail(Order order, List<OrderDetailFood> detailFoods, List<TPayType> tPayTypes, int position) {
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra(Config.PARAM1, order);
        intent.putExtra(Config.PARAM2, (Serializable) detailFoods);
        intent.putExtra(Config.PARAM3, position);
        intent.putExtra(Config.PARAM4, OrderDetailActivity.P3);
        intent.putExtra(Config.PARAM5, (Serializable) tPayTypes);
        startActivityForResult(intent, 109);
    }

    private void initRecyclerView() {
        adapter = new OrderAdapter(R.layout.item_order);
        adapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.parseColor("#F8F8F8")).sizeResId(R.dimen._1sdp).build());
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            presenter.getOrderDetail((Order) adapter.getItem(position), position);
        });
    }

    private void initPTRLayout() {
        MaterialHeader header = new MaterialHeader(this);
        int c[] = {ContextCompat.getColor(this, R.color.colorPrimary)};
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent event) {
        switch (event.getType()) {
            case MsgEvent.message:
                int i = (int) event.getData();
                adapter.remove(i);
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getMessage(index, size);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            index = 1;
            presenter.getMessage(index, size);
        }
    }
}
