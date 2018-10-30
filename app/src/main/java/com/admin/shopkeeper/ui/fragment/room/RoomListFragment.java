package com.admin.shopkeeper.ui.fragment.room;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.RoomsAdapter;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.ui.activity.config.room.ConfigRoomActivity;
import com.admin.shopkeeper.ui.activity.config.room.ConfigRoomPresenter;
import com.admin.shopkeeper.ui.activity.config.room.IConfigRoomView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

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
 * Use the {@link RoomListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomListFragment extends BaseFragment<ConfigRoomPresenter> implements IConfigRoomView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.ptrLayout)
    PtrFrameLayout ptrLayout;

    private RoomsAdapter adapter;

    public static RoomListFragment newInstance() {
        RoomListFragment fragment = new RoomListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_room_list;
    }

    @Override
    protected void initPresenter() {

        presenter = new ConfigRoomPresenter(getActivity(), this);
        presenter.init();
    }

    @Override
    public void initView() {
        initPTRLayout();
        initRecyclerView();

        if (TextUtils.isEmpty(App.INSTANCE().getShopID())) {
            warning("请先配置店铺ID");
            return;
        }

        presenter.getDB();
    }

    private void initRecyclerView() {
        adapter = new RoomsAdapter(R.layout.item_room);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .sizeResId(R.dimen._1sdp)
                .colorResId(R.color.gray)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .build());
        recyclerView.setAdapter(adapter);
    }

    private void initPTRLayout() {

        MaterialHeader header = new MaterialHeader(getActivity());
        int c[] = {ContextCompat.getColor(getActivity(), R.color.colorPrimary)};
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
                presenter.getServers();
            }
        });

    }

    @Override
    public void error(String message) {
        refreshComplete();
        Toasty.error(getActivity(), message, Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void showRooms(List<RoomEntity> roomEntities) {
        refreshComplete();
        if (roomEntities.size() > 0) {
            adapter.setNewData(roomEntities);
        }
        Toasty.normal(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void warning(String message) {
        refreshComplete();
        Toasty.warning(getActivity(), message, Toast.LENGTH_SHORT, true).show();
    }

    private void refreshComplete() {
        if (ptrLayout.isRefreshing()) {
            ptrLayout.refreshComplete();
        }
    }

    @Override
    public void noDataWithDB() {

        ptrLayout.postDelayed(() -> ptrLayout.autoRefresh(), 100);
    }
}
