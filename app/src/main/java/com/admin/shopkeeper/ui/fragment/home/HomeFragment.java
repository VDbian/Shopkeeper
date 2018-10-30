package com.admin.shopkeeper.ui.fragment.home;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.SaleBussinessAdapter;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.entity.BussinessBean;
import com.admin.shopkeeper.entity.FoodBussinessBean;
import com.admin.shopkeeper.utils.Tools;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * Created by Administrator on 2017/8/24.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements IHomeView {

    @BindView(R.id.state_totle)
    TextView tvTotle;
    @BindView(R.id.state_totle_recharge)
    TextView tvTotleOfRecharge;
    @BindView(R.id.state_youhui)
    TextView tvYouHui;
    @BindView(R.id.state_totle_sale)
    TextView tvTotleOfSale;
    @BindView(R.id.state_customer_nums)
    TextView tvCustomerNums;
    @BindView(R.id.state_unit_price)
    TextView tvPrice;
    @BindView(R.id.state_cancel_order_nums)
    TextView tvCancleOrderNums;
    @BindView(R.id.state_rebill_nums)
    TextView tvRebillNums;
    @BindView(R.id.state_refresh)
    PtrFrameLayout frameLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;

    private SaleBussinessAdapter adapter;
    private SaleBussinessAdapter adapter1;

    @Override
    public void initView() {
        MaterialHeader header = new MaterialHeader(getActivity());
        int c[] = {ContextCompat.getColor(getActivity(), R.color.bosscolorPrimary)};
        header.setColorSchemeColors(c);
        header.setPadding(0, PtrLocalDisplay.dp2px(20), 0, PtrLocalDisplay.dp2px(20));
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        frameLayout.setDurationToCloseHeader(1000);
        frameLayout.setHeaderView(header);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setPinContent(true);//设置为true时content的内容位置将不会改变

        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.getData();
                presenter.getData("8", "1999-01-01", Tools.formatNowDate("yyyy-MM-dd"));
                presenter.getData("9", "1999-01-01", Tools.formatNowDate("yyyy-MM-dd"));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new SaleBussinessAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView1.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter1 = new SaleBussinessAdapter(getActivity());
        recyclerView1.setAdapter(adapter1);

        presenter.getData("8", "1999-01-01", Tools.formatNowDate("yyyy-MM-dd"));
        presenter.getData("9", "1999-01-01", Tools.formatNowDate("yyyy-MM-dd"));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initPresenter() {
        presenter = new HomePresenter(getActivity(), this);
        presenter.init();
    }

    /*@OnClick(R.id.state_bussiness)
    public void bussinessClick() {
        startActivity(BussinessActivity.class);
    }

    @OnClick(R.id.state_sale)
    public void saleClick() {
        startActivity(SaleRankActivity.class);
    }

    @OnClick(R.id.state_return)
    public void returnClick() {
        startActivity(ReturnAnalysisActivity.class);
    }*/

    @Override
    public void error(String msg) {
        frameLayout.refreshComplete();
        showFailToast(msg);
    }

    @Override
    public void success(List<BussinessBean> bussinessBeen) {
        frameLayout.refreshComplete();
        if (bussinessBeen.size() > 0) {
            BussinessBean bean = bussinessBeen.get(0);
            tvTotle.setText("" + bean.getChargeMoney());
            tvTotleOfRecharge.setText("总充值收入\n" + bean.getTableCount());
            tvYouHui.setText("优惠金额\n" + bean.getFreeMoney());
            tvTotleOfSale.setText("销售总实收\n" + (bean.getTotalMoney()));

            tvCustomerNums.setText((bean.getPersonCount() + "\n来客人数"));
            tvPrice.setText((bean.getCount() + "\n客单价"));
            tvCancleOrderNums.setText((bean.getCheTableCount() + "\n撤单次数"));
            tvRebillNums.setText((bean.getFanCount() + "\n反结账次数"));
        }
    }

    @Override
    public void successOfHotFood(List<FoodBussinessBean> data) {
        adapter.setDatas(data);
    }

    @Override
    public void successOfCoolFood(List<FoodBussinessBean> data) {
        adapter1.setDatas(data);
    }
}
