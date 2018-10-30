package com.admin.shopkeeper.ui.fragment.setting;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseFragment;
import com.admin.shopkeeper.entity.BossUserInfo;
import com.admin.shopkeeper.ui.activity.activityOfBoss.basicSets.BasicSetsActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.deskmanager.DeskManagerActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.desktype.DeskTypeActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.foodmanager.FoodManagerActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.house.HouseActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.mealManger.MealManagerActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.mealTypemanager.MealTypeManagerActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.my.MyActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.print.PrintManagerActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.returncause.ReturnCauseActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.sale.SaleActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.shopPermissionManage.ShopPermissionManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.staffManager.StaffManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.wechat.WechatActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.weight.WeightActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.wxsetting.WXSettingActivity;
import com.admin.shopkeeper.ui.activity.login.LoginActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


/**
 * Created by Administrator on 2017/8/24.
 */

public class SettingFragment extends BaseFragment<SettingPresenter> implements ISettingView {

    @BindView(R.id.my_rl_setting)
    RelativeLayout myRL;
    @BindView(R.id.basic_sets_rl_setting)
    RelativeLayout basicSetsRL;
    @BindView(R.id.shop_permission_manage_rl_setting)
    RelativeLayout shopPermissionManageRL;
    @BindView(R.id.username_setting)
    TextView tvUserName;
    @BindView(R.id.restaurant_name_setting)
    TextView tvShopName;

    @Override
    public void initView() {
        presenter.queryUserInfo(App.INSTANCE().getShopID());
    }

    @OnClick(R.id.my_rl_setting)
    public void myRLClick() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        if (bossUserInfo != null) {
            intent.putExtra("BOSSUSERINFO", bossUserInfo);
        }
        startActivity(intent);
    }

    @OnClick(R.id.basic_sets_rl_setting)
    public void setBasicSetsRLClick() {
        Intent intent = new Intent(getActivity(), BasicSetsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.shop_permission_manage_rl_setting)
    public void setShopPermissionManageRLClick() {
        Intent intent = new Intent(getActivity(), ShopPermissionManageActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.staff_manage_ll)
    public void staffManageClick() {
        startActivity(StaffManageActivity.class);
    }
    @OnClick(R.id.basic_retcause)
    public void retcauseClick() {
        startActivity(ReturnCauseActivity.class);
    }

    @OnClick(R.id.basic_commodity)
    public void commodityClick() {
        startActivity(FoodManagerActivity.class);
    }

    @OnClick(R.id.basic_meal_manage)
    public void mealManageClick() {
        startActivity(MealManagerActivity.class);
    }

    @OnClick(R.id.basic_meal_type_manage)
    public void mealTypeManageClick() {
        startActivity(MealTypeManagerActivity.class);
    }

    @OnClick(R.id.basic_print)
    public void printClick() {
        startActivity(PrintManagerActivity.class);
    }

    @OnClick(R.id.basic_sale)
    public void saleClick() {
        startActivity(SaleActivity.class);
    }

    @OnClick(R.id.basic_weight)
    public void weightClick() {
        startActivity(WeightActivity.class);
    }

    @OnClick(R.id.basic_house)
    public void houseClick() {
        startActivity(HouseActivity.class);
    }

    @OnClick(R.id.basic_desk)
    public void deskClick() {
        startActivity(DeskManagerActivity.class);
    }

    @OnClick(R.id.basic_desktype)
    public void desktypeClick() {
        startActivity(DeskTypeActivity.class);
    }

    @OnClick(R.id.market_weixin)
    public void wechatClick() {
        Intent intent = new Intent(getActivity(), WechatActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_wxsetting)
    public void wechat2Click() {
        Intent intent = new Intent(getActivity(), WXSettingActivity.class);
        startActivity(intent);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initPresenter() {
        presenter = new SettingPresenter(getActivity(), this);
        presenter.init();
    }

    @OnClick(R.id.setting_unlogin)
    public void unloginClick() {
        startActivity(LoginActivity.class);
        getActivity().finish();
    }

    @OnClick(R.id.setting_checkdata)
    public void checkClick(){
        presenter.checkData();
    }

    @Override
    public void showError(String message) {
        Toasty.error(getContext(), message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void showFailed(String failed) {
        Toasty.warning(getContext(), failed, Toast.LENGTH_SHORT, true).show();
    }

    BossUserInfo bossUserInfo;

    @Override
    public void getInfoSuccess(List<BossUserInfo> bossUserInfos) {
        bossUserInfo = bossUserInfos.get(0);
        tvUserName.setText(bossUserInfo.getName());
        tvShopName.setText(bossUserInfo.getShopNames());
        if (!TextUtils.isEmpty(bossUserInfos.get(0).getAddress())) {
            App.INSTANCE().setShopAddress(bossUserInfos.get(0).getAddress());
        }
        if (!TextUtils.isEmpty(bossUserInfos.get(0).getShopNames())) {
            App.INSTANCE().setShopName(bossUserInfos.get(0).getShopNames());
        }
        if (!TextUtils.isEmpty(bossUserInfos.get(0).getName())) {
            App.INSTANCE().setUserNameOfBoss(bossUserInfos.get(0).getName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        tvShopName.setText(App.INSTANCE().getShopName());
    }
}
