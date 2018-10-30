package com.admin.shopkeeper.ui.fragment.statement;

import android.content.Intent;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.collectionStatistics.CollectionStatisticsActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.couponDetailTable.CouponDetailTableActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.deskopen.DeskOpenActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.free.FreeActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.giftstatistics.GiftStatisticsActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.integralTransactionDetail.IntegralTransactionDetailActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.jion.JionActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberVolumeAnalysis.MemberVolumeAnalysisActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberconsumeDetails.MemberConsumeDetailActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeDetail.RechargeDetailActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeTranscation.RechargeTranscationActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.saleStatisticsProduct.SaleStatisticsProductActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.shopcollection.ShopCollectionActivity;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/24.
 */

public class StatementFragment extends BaseFragment<StatementPresenter> implements IStatementView {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_statement;
    }

    @Override
    protected void initPresenter() {
        presenter = new StatementPresenter(getActivity(), this);
        presenter.init();
    }

    @Override
    public void initView() {
        presenter.getChain();
    }

    @Override
    public void chainsuccess(List<ChainBean> chainBeans) {
        App.INSTANCE().setChainBeans(chainBeans);
    }

    @OnClick(R.id.staff_manage_ll)
    public void shopCollectionClick() {
        //Intent intent = new Intent(getActivity(), ShopCollectionActivity.class);
        Intent intent = new Intent(getActivity(), CollectionStatisticsActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
    }

    @OnClick(R.id.member_manage_ll)
    public void chainCollectionClick() {
        Intent intent = new Intent(getActivity(), CollectionStatisticsActivity.class);
        intent.putExtra("type", 2);
        startActivity(intent);
    }

    @OnClick(R.id.basic_print)
    public void thirdCollectionClick() {
        startActivity(ShopCollectionActivity.class);
    }

    @OnClick(R.id.basic_commodity)
    public void handoverClick() {
        startActivity(JionActivity.class);
    }

    @OnClick(R.id.basic_weight)
    public void freeClick() {
        startActivity(FreeActivity.class);
    }

    @OnClick(R.id.basic_sale)
    public void deskRateClick() {
        startActivity(DeskOpenActivity.class);
    }

    @OnClick(R.id.basic_guazhang)
    public void saleStatisticsClick() {
        startActivity(SaleStatisticsProductActivity.class);
    }

    @OnClick(R.id.basic_retcause)
    public void giftStatisticsClick() {
        startActivity(GiftStatisticsActivity.class);
    }


    @OnClick(R.id.statement_transaction_ll)
    public void statementTranscationClick() {
        startActivity(RechargeTranscationActivity.class);
    }

    @OnClick(R.id.statement_member_recharge_ll)
    public void statementDetailClick() {
        startActivity(RechargeDetailActivity.class);
    }

    @OnClick(R.id.statement_member_volume_ll)
    public void statementMemberVolumeClick() {
        startActivity(MemberVolumeAnalysisActivity.class);
    }

    @OnClick(R.id.statement_member_consume_detail_ll)
    public void statementMemberConsumeDetailClick() {
        startActivity(MemberConsumeDetailActivity.class);
    }

    @OnClick(R.id.statement_coupon_ll)
    public void couponClick() {
        startActivity(CouponDetailTableActivity.class);
    }

    @OnClick(R.id.statement_integral_ll)
    public void statementIntegralClick() {
        startActivity(IntegralTransactionDetailActivity.class);
    }


}
