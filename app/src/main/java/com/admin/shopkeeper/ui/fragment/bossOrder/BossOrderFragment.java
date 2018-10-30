package com.admin.shopkeeper.ui.fragment.bossOrder;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.ui.activity.activityOfBoss.guazhang.GuaZhangActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.orderManage.OrderManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.returnstatistics.ReturnStatisticsActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.sensitiveOpearation.SensitiveOpearationActivity;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/24.
 */

public class BossOrderFragment extends BaseFragment<BasicPresenter> implements IBossOrderView {
    @Override
    public void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_basic;
    }

    @Override
    protected void initPresenter() {
        presenter = new BasicPresenter(getActivity(), this);
        presenter.init();
    }
    @OnClick(R.id.order_order_manage)
    public void orderClick() {
        startActivity(OrderManageActivity.class);
    }
    @OnClick(R.id.order_guazhang)
    public void guazhangClick() {
        startActivity(GuaZhangActivity.class);
    }
    @OnClick(R.id.order_sensitive_opearation)
    public void sensitiveOpearationClick() {
        startActivity(SensitiveOpearationActivity.class);
    }
    @OnClick(R.id.order_return_foot)
    public void returnStatisticsClick() {
        startActivity(ReturnStatisticsActivity.class);
    }

}
