package com.admin.shopkeeper.ui.activity.activityOfBoss.boss;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.CollectionSelectDialog;
import com.admin.shopkeeper.entity.BossUserInfo;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.my.MyActivity;
import com.admin.shopkeeper.ui.activity.login.LoginActivity;
import com.admin.shopkeeper.ui.fragment.bossOrder.BossOrderFragment;
import com.admin.shopkeeper.ui.fragment.market.MarketFragment;
import com.admin.shopkeeper.ui.fragment.setting.SettingFragment;
import com.admin.shopkeeper.ui.fragment.home.HomeFragment;
import com.admin.shopkeeper.ui.fragment.statement.StatementFragment;
import com.admin.shopkeeper.utils.SPUtils;
import com.admin.shopkeeper.utils.UIUtils;
import com.admin.shopkeeper.weight.HomeViewPager;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class BossMainActivity extends BaseActivity<BossMainPresenter> implements IBossMainView, NavigationView.OnNavigationItemSelectedListener {

    public static final String[] tabs = {"首页", "报表", "订单", "营销", "设置"};


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.viewPager)
    HomeViewPager viewPager;
    @BindView(R.id.tab_home)
    TextView tvHome;
    @BindView(R.id.tab_statement)
    TextView tvStatistics;
    @BindView(R.id.tab_order_list)
    TextView tvOrderList;
    @BindView(R.id.tab_market)
    TextView tvMarket;
    @BindView(R.id.tab_setting)
    TextView tvSetting;

    Map<String, Fragment> fragments = new HashMap<>();
    private BossUserInfo bossUserInfo;

    TextView tvShopName;
    TextView tvUser;

    @Override
    protected void initPresenter() {
        presenter = new BossMainPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_boss;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("首页");
        toolbar.setNavigationIcon(R.mipmap.person_advar_man);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toolbar.setNavigationOnClickListener(v -> {
            drawer.openDrawer(GravityCompat.START);
        });

        View headerView = navigationView.getHeaderView(0);

        tvShopName = (TextView) headerView.findViewById(R.id.restaurant_name_setting);
        tvUser = (TextView) headerView.findViewById(R.id.username_setting);

        navigationView.setNavigationItemSelectedListener(this);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return tabs.length;
            }

            @Override
            public Fragment getItem(int position) {
                Fragment fragment = fragments.get(tabs[position]);
                if (fragment != null) {
                    return fragment;
                }
                switch (position) {
                    case 0:
                        fragment = new HomeFragment();
                        break;
                    case 1:
                        fragment = new StatementFragment();
                        break;
                    case 2:
                        fragment = new BossOrderFragment();
                        break;
                    case 3:
                        fragment = new MarketFragment();
                        break;
                    case 4:
                        fragment = new SettingFragment();
                        break;
                }
                return fragment;
            }
        });
        viewPager.setNoScroll(true);

        presenter.queryUserInfo(App.INSTANCE().getShopID());
    }

    @OnClick(R.id.tab_home)
    public void homeClick() {
        changePage(0);
    }

    @OnClick(R.id.tab_statement)
    public void statementClick() {
        changePage(1);
    }

    @OnClick(R.id.tab_order_list)
    public void orderListClick() {
        changePage(2);
    }

    @OnClick(R.id.tab_market)
    public void marketClick() {
        changePage(3);
    }

    @OnClick(R.id.tab_setting)
    public void settingClick() {
        changePage(4);
    }

    private void changePage(int index) {
        if (index == viewPager.getCurrentItem()) {
            return;
        }
        viewPager.setCurrentItem(index, false);
//        "首页", "报表", "订单", "营销", "设置"
        switch (index) {
            case 0:
                //setImmersionBar(true, "首页");
                toolbar.setTitle("首页");
                tvHome.setTextColor(getResources().getColor(R.color.navbar_selected_two));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvOrderList.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_normal));

                UIUtils.setDrawableTop(tvHome, R.mipmap.menu_bar_icon_home_fill);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.menu_bar_icon_statistics);
                UIUtils.setDrawableTop(tvOrderList, R.mipmap.menu_bar_icon_orderlist);
                UIUtils.setDrawableTop(tvMarket, R.mipmap.menu_bar_icon_marketing);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings);
                break;

            case 1:
                toolbar.setTitle("报表");
                tvHome.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_selected_two));
                tvOrderList.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_normal));

                UIUtils.setDrawableTop(tvHome, R.mipmap.menu_bar_icon_home);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.menu_bar_icon_statistics_fill);
                UIUtils.setDrawableTop(tvOrderList, R.mipmap.menu_bar_icon_orderlist);
                UIUtils.setDrawableTop(tvMarket, R.mipmap.menu_bar_icon_marketing);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings);
                break;

            case 2:
                toolbar.setTitle("订单");
                tvHome.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvOrderList.setTextColor(getResources().getColor(R.color.navbar_selected_two));
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_normal));

                UIUtils.setDrawableTop(tvHome, R.mipmap.menu_bar_icon_home);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.menu_bar_icon_statistics);
                UIUtils.setDrawableTop(tvOrderList, R.mipmap.menu_bar_icon_orderlist_fill);
                UIUtils.setDrawableTop(tvMarket, R.mipmap.menu_bar_icon_marketing);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings);
                break;
            case 3:
                toolbar.setTitle("营销");
                tvHome.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvOrderList.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_selected_two));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_normal));

                UIUtils.setDrawableTop(tvHome, R.mipmap.menu_bar_icon_home);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.menu_bar_icon_statistics);
                UIUtils.setDrawableTop(tvOrderList, R.mipmap.menu_bar_icon_orderlist);
                UIUtils.setDrawableTop(tvMarket, R.mipmap.menu_bar_icon_marketing_fill);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.navbar_settings);
                break;
            case 4:
                toolbar.setTitle("设置");
                tvHome.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvStatistics.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvOrderList.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvMarket.setTextColor(getResources().getColor(R.color.navbar_normal));
                tvSetting.setTextColor(getResources().getColor(R.color.navbar_selected_two));

                UIUtils.setDrawableTop(tvHome, R.mipmap.menu_bar_icon_home);
                UIUtils.setDrawableTop(tvStatistics, R.mipmap.menu_bar_icon_statistics);
                UIUtils.setDrawableTop(tvOrderList, R.mipmap.menu_bar_icon_orderlist);
                UIUtils.setDrawableTop(tvMarket, R.mipmap.menu_bar_icon_marketing);
                UIUtils.setDrawableTop(tvSetting, R.mipmap.menu_bar_icon_settings_fill);
                break;
        }
    }

    /**
     * @param isNavigationIcon 是否有头像
     * @param title            toolbar标题
     */
    private void setImmersionBar(boolean isNavigationIcon, String title) {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle(title);
        if (isNavigationIcon) {
            toolbar.setNavigationIcon(R.mipmap.person_advar_man);
        } else {
            toolbar.setNavigationIcon(null);
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public void getInfoSuccess(List<BossUserInfo> bossUserInfos) {
        bossUserInfo = bossUserInfos.get(0);
        if (!TextUtils.isEmpty(bossUserInfo.getAddress())) {
            App.INSTANCE().setShopAddress(bossUserInfo.getAddress());
        }
        if (!TextUtils.isEmpty(bossUserInfo.getShopNames())) {
            App.INSTANCE().setShopName(bossUserInfo.getShopNames());
        }
        if (!TextUtils.isEmpty(bossUserInfo.getName())) {
            App.INSTANCE().setUserNameOfBoss(bossUserInfo.getName());
        }
        tvShopName.setText(bossUserInfo.getShopNames());
        tvUser.setText(bossUserInfo.getName());
    }

    @Override
    public void getChainInfoSuccess(List<ChainBean> chainBeanList) {
        CollectionSelectDialog.Builder builder = new CollectionSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("选择门店");
        builder.setReasons(chainBeanList);
        builder.setSelect(App.INSTANCE().getShopName());
        builder.setSingleSelect(true);
        builder.setButtonClick((text, value) -> {
            App.INSTANCE().setShopName(text);
            App.INSTANCE().setShopID(value);
            presenter.queryUserInfo(App.INSTANCE().getShopID());
        });
        builder.creater().show();
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        setDrawerMenu(id);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setDrawerMenu(int id) {
        AlertDialog.Builder builder;
        switch (id) {
            case R.id.nav_user:
                Intent intent = new Intent(this, MyActivity.class);
                if (bossUserInfo != null) {
                    intent.putExtra("BOSSUSERINFO", bossUserInfo);
                }
                startActivity(intent);
                break;
            case R.id.nav_datas:
                presenter.checkData();
                break;
            case R.id.check_shop:
                presenter.checkShop();
                break;
            case R.id.nav_exit:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("是否退出登录？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtils.getInstance().put(SPUtils.PREFERENCE_LOGIN, false);
                        SPUtils.getInstance().put(SPUtils.PREFERENCE_USER, "");
                        Intent intent = new Intent(BossMainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        App.INSTANCE().removeAlias(App.INSTANCE().getShopID(), "PHONE");
                        finish();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
                break;
        }

    }
}
