package com.admin.shopkeeper.ui.activity.main;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.ui.fragment.desk.DeskFragment;
import com.admin.shopkeeper.ui.fragment.message.MessageFragment;
import com.admin.shopkeeper.ui.fragment.more.MoreFragment;
import com.admin.shopkeeper.ui.fragment.order.OrderFragment;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gyf.barlibrary.ImmersionBar;



import butterknife.BindView;
import timber.log.Timber;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, BottomNavigationBar.OnTabSelectedListener {


    @BindView(R.id.bottomBar_main)
    BottomNavigationBar bar;

//    @BindView(R.id.headerLayout)
//    ConstraintLayout header;

    private BottomNavigationItem messageItem;
    private BottomNavigationItem orderItem;
    private BottomNavigationItem deskItem;
    private BottomNavigationItem moreItem;

    private MessageFragment messageFragment;
    private OrderFragment orderFragment;
    private DeskFragment deskFragment;
    private MoreFragment moreFragment;

    private SearchView searchView;
    private MenuItem menuItem;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void initPresenter() {
        presenter = new MainPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("消息");
        setSupportActionBar(toolbar);

        initBottom();
        initFragment();
        setFragment( 0);
    }

    private void initFragment() {
        messageFragment = MessageFragment.newInstance("", "");
        orderFragment = OrderFragment.newInstance("", "");
        deskFragment = DeskFragment.newInstance("", "");
        moreFragment = MoreFragment.newInstance();
    }

    private void initBottom() {
        bar.setMode(BottomNavigationBar.MODE_FIXED);


        bar.setInActiveColor("#7f8389")//设置未选中的Item的颜色，包括图片和文字
                .setActiveColor("#ff2d4b")
                .setBarBackgroundColor(R.color.colorWhite);//设置整个控件的背景色

        messageItem = new BottomNavigationItem(R.mipmap.tabbar_icon_message, "消息");
        orderItem = new BottomNavigationItem(R.mipmap.tabbar_icon_order, "订单");
        deskItem = new BottomNavigationItem(R.mipmap.tabbar_icon_desk, "堂点");
        moreItem = new BottomNavigationItem(R.mipmap.tabbar_icon_more, "更多");
        bar.addItem(messageItem)
                .addItem(orderItem)
                .addItem(deskItem)
                .addItem(moreItem)
                .initialise();


        bar.setTabSelectedListener(this);

    }


    private int index = 0;

    /**
     * @param position
     */
    private void setFragment( int position) {


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();


        switch (position) {
            case 0:

                if (!messageFragment.isAdded()) {
                    transaction.add(R.id.content_main, messageFragment);
                } else if (messageFragment.isHidden()) {
                    transaction.show(messageFragment);
                } else {
                    transaction.hide(messageFragment);
                }
                orderFragment.closeMenu();

                break;
            case 1:

                if (!orderFragment.isAdded()) {
                    transaction.add(R.id.content_main, orderFragment);

                } else if (orderFragment.isHidden()) {
                    transaction.show(orderFragment);

                } else {
                    transaction.hide(orderFragment);
                }
                break;
            case 2:
                if (!deskFragment.isAdded()) {
                    transaction.add(R.id.content_main, deskFragment);
                } else if (deskFragment.isHidden()) {
                    transaction.show(deskFragment);

                } else {
                    transaction.hide(deskFragment);
                }
                orderFragment.closeMenu();
                break;
            case 3:
                if (!moreFragment.isAdded()) {
                    transaction.add(R.id.content_main, moreFragment);

                } else if (moreFragment.isHidden()) {
                    transaction.show(moreFragment);

                } else {
                    transaction.hide(moreFragment);
                }
                orderFragment.closeMenu();
                break;
        }

        transaction.commit();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public void onTabSelected(int position) {
        Timber.d("onTabSelected-->" + position);
        setFragment(position);
        index = position;
        invalidateOptionsMenu();

    }

    @Override
    public void onTabUnselected(int position) {
        Timber.d("onTabUnselected-->" + position);
        setFragment(position);
    }

    @Override
    public void onTabReselected(int position) {
        Timber.d("onTabReselected-->" + position);
        // setFragment(true, position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        menuItem.setOnMenuItemClickListener(item -> {
            if (orderFragment.isVisible() && orderFragment.menuIsVisible()) {

                orderFragment.closeMenu();

            }
            return true;
        });
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.onActionViewCollapsed();


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        Timber.d(index + "ddddd");
        switch (index) {
            case 0:
                menuItem.setVisible(false);
                if (menuItem.collapseActionView())
                    searchView.onActionViewCollapsed();
                toolbar.setTitle("消息");
                break;
            case 1:
                menuItem.setVisible(true);
                toolbar.setTitle("订单");
                break;
            case 2:
                menuItem.setVisible(false);
                if (menuItem.collapseActionView())
                    searchView.onActionViewCollapsed();
                toolbar.setTitle("堂点");
                break;
            case 3:
                menuItem.setVisible(false);
                if (menuItem.collapseActionView())
                    searchView.onActionViewCollapsed();
                toolbar.setTitle("");
                break;
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        if (orderFragment.isVisible() && orderFragment.menuIsVisible()) {

            orderFragment.closeMenu();

        } else {
            super.onBackPressed();
        }
    }
}
