package com.admin.shopkeeper.ui.fragment.market;


import android.content.Intent;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.ui.activity.activityOfBoss.couponLineDown.CouponLineDownActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.couponManage.CouponManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.mansong.MansongActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberLevelManage.MemberLevelManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.memberManager.MemberManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeManage.RechargeManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.wechat.WechatActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.wxsetting.WXSettingActivity;

import butterknife.OnClick;


public class MarketFragment extends BaseFragment<MarketPresenter> implements IMarketView {


    @Override
    public void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_market;
    }

    @Override
    protected void initPresenter() {
        presenter = new MarketPresenter(getActivity(), this);
        presenter.init();
    }

    //会员等级管理
    @OnClick(R.id.market_member_level_manage_ll)
    public void setMemberLevelManageClick() {
        startActivity(MemberLevelManageActivity.class);
    }

    //会员交易管理
    @OnClick(R.id.member_manage_ll)
    public void setMemberManageClick() {
        startActivity(MemberManageActivity.class);
    }

    //充值方案管理
    @OnClick(R.id.market_recharge_manage_ll)
    public void setRechargeManageClick() {
        startActivity(RechargeManageActivity.class);
    }

    //微信券管理
    @OnClick(R.id.market_coupon_manage_ll)
    public void setCouponManageClick() {
        Intent intent = new Intent(getActivity(), CouponManageActivity.class);
        intent.putExtra(Config.PARAM1, "微信券管理");
        startActivity(intent);
    }

   /* //团购券管理
    @OnClick(R.id.market_group_manage_ll)
    public void setGroupManageClick() {
        Intent intent = new Intent(getActivity(), CouponManageActivity.class);
        intent.putExtra(Config.PARAM1, "团购券管理");
        startActivity(intent);
    }

    //商品券管理
    @OnClick(R.id.market_commodity_coupon_manage_ll)
    public void setCommodityManageClick() {
        Intent intent = new Intent(getActivity(), CouponManageActivity.class);
        intent.putExtra(Config.PARAM1, "商品券管理");
        startActivity(intent);
    }*/

//    @OnClick(R.id.market_weixin)
//    public void wechatClick() {
//        Intent intent = new Intent(getActivity(), WechatActivity.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.market_weixin_setting)
//    public void wechat2Click() {
//        Intent intent = new Intent(getActivity(), WXSettingActivity.class);
//        startActivity(intent);
//    }

    @OnClick(R.id.market_ll_mansong)
    public void mansongClick() {
        startActivity(MansongActivity.class);
    }

    @OnClick(R.id.market_ll_line_down)
    public void lineDownClick() {
        startActivity(CouponLineDownActivity.class);
    }

}
