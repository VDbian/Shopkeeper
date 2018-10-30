package com.admin.shopkeeper.ui.activity.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.ui.activity.login.LoginActivity;
import com.admin.shopkeeper.ui.fragment.food.FoodsFragment;

import com.admin.shopkeeper.ui.fragment.more.MoreFragment;
import com.admin.shopkeeper.ui.fragment.room.RoomListFragment;
import com.admin.shopkeeper.ui.fragment.setMeal.MealFragment;
import com.admin.shopkeeper.utils.SPUtils;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;


public class HomeActivity extends BaseActivity<HomePresenter>
        implements NavigationView.OnNavigationItemSelectedListener, IHomeView, OnPermissionCallback {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private MoreFragment moreFragment;
    private RoomListFragment roomListFragment;
    private FoodsFragment foodsFragment;
    private MealFragment mealFragment;

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private String TAG_FRAGMENT[] = new String[4];
    private String SAVED_INDEX = "save_index";
    private int index = 0;
    private PermissionHelper permissionHelper;


    @Override
    protected void initPresenter() {
        presenter = new HomePresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        setDrawerMenu(id);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("主页");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        permissionHelper = PermissionHelper.getInstance(this);
        permissionHelper
                .setForceAccepting(false)// true if you had like force reshowing the permission dialog on Deny (not recommended)
                .request(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA});
    }

    private void setDrawerMenu(int id) {
        AlertDialog.Builder builder;
        switch (id) {
            case R.id.nav_home:
                toolbar.setTitle("主页");
                setSelect(0);
                break;
            case R.id.nav_room:
                toolbar.setTitle("房间刷新");
                //setSelect(1);
                presenter.getServers();
                break;
            case R.id.nav_foods:
                toolbar.setTitle("菜品刷新");
                //setSelect(2);
                AppDbHelper.INSTANCE().deleteAll();
                presenter.getFoods();
                //presenter.getMeal();
                break;
            case R.id.nav_exit:
                builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否退出当前账号？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtils.getInstance().put(SPUtils.PREFERENCE_LOGIN, false);
                        SPUtils.getInstance().put(SPUtils.PREFERENCE_USER, "");
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        App.INSTANCE().removeAlias(App.INSTANCE().getShopID(), "PHONE");
                        finish();
                    }
                });
                builder.show();
                break;
            case R.id.nav_id:
                builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否本机作为打印主机");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.INSTANCE().setAlias(App.INSTANCE().getShopID(), "PHONE");
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                break;

            case R.id.nav_cancel:
                builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否取消本机作为打印主机？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.INSTANCE().removeAlias(App.INSTANCE().getShopID(), "PHONE");
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        // setTab(i);
        // mViewPager.setCurrentItem(i);
        // 把图片设置为亮的
        // 设置内容区域
        switch (i) {
            case 0:
                if (moreFragment == null) {
                    moreFragment = MoreFragment.newInstance();
                    //设置tag值
                    transaction.add(R.id.contentHome, moreFragment, TAG_FRAGMENT[0]);
                } else {
                    transaction.show(moreFragment);
                }

                break;
            case 1:
                if (roomListFragment == null) {
                    roomListFragment = RoomListFragment.newInstance();
                    transaction.add(R.id.contentHome, roomListFragment, TAG_FRAGMENT[1]);
                } else {
                    transaction.show(roomListFragment);
                }
                break;
            case 2:
                if (foodsFragment == null) {
                    foodsFragment = FoodsFragment.newInstance();
                    transaction.add(R.id.contentHome, foodsFragment, TAG_FRAGMENT[2]);
                } else {
                    transaction.show(foodsFragment);
                }
                break;

            case 3:
                if (mealFragment == null) {
                    mealFragment = MealFragment.newInstance();
                    transaction.add(R.id.contentHome, mealFragment, TAG_FRAGMENT[3]);
                } else {
                    transaction.show(mealFragment);
                }
                break;

        }

        transaction.commit();
        index = i;
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
        if (roomListFragment != null) {
            transaction.hide(roomListFragment);
        }
        if (foodsFragment != null) {
            transaction.hide(foodsFragment);
        }
        if (mealFragment != null) {
            transaction.hide(mealFragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVED_INDEX, index);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(SAVED_INDEX, index);
            moreFragment = (MoreFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT[0]);
            roomListFragment = (RoomListFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT[1]);
            foodsFragment = (FoodsFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT[2]);
        }
        setSelect(index);
    }

    @Override
    public void error(String message) {
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void warning(String s) {
        Toasty.warning(this, s, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void success(List<MenuTypeEntity> menuTypeEntities) {
        Toasty.normal(this, "刷新成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRooms(List<RoomEntity> roomEntities) {
        Toasty.normal(this, "刷新成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {

    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {

    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {

    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {

    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {

    }

    @Override
    public void onNoPermissionNeeded() {

    }
}
