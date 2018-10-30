package com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeManage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.MemberLevelManagerAdapter;
import com.admin.shopkeeper.adapter.RechargeManagerAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.MemberLevelManageBean;
import com.admin.shopkeeper.entity.RechargeManageBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.addMemberLevel.AddMemberLevelActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeManageEdit.RechargeManageEditActivity;
import com.admin.shopkeeper.utils.UIUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargeManageActivity extends BaseActivity<RechargeManagePresenter> implements IRechargeManageView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recharge_manage_default)
    TextView tvDefault;
    @BindView(R.id.recharge_money)
    TextView tvRechargeMoney;
    @BindView(R.id.recharge_give_money)
    TextView tvRechargeGiveMoney;
    @BindView(R.id.recharge_manage_select)
    TextView tvSelect;
    private RechargeManagerAdapter adapter;
    private PopupWindow laheiPop;
    List<RechargeManageBean> data;
    private PopupWindow popupWindow;

    @Override
    protected void initPresenter() {

        presenter = new RechargeManagePresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_manage;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("充值方案管理");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._1sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new RechargeManagerAdapter(this);
        recyclerView.setAdapter(adapter);
        presenter.getRechargeManageInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.getRechargeManageInfo();
        }
        if (laheiPop != null && laheiPop.isShowing()) {
            laheiPop.dismiss();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add:
                Intent intent = new Intent(this, RechargeManageEditActivity.class);
                startActivityForResult(intent, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    int defaultType = 0;

    @OnClick(R.id.recharge_manage_default)
    public void setDefaultClick() {
        defaultType++;

        if (data == null) {
            return;
        }
        if (defaultType % 3 == 1) {
            UIUtils.setDrawableRight(tvDefault, R.mipmap.sort_a_z);
            List<RechargeManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (o1.getPosition() > o2.getPosition()) {
                    return 1;
                } else if (o1.getPosition() == o2.getPosition()) {
                    return 0;
                } else {
                    return -1;
                }
            });
            adapter.setDatas(newData);
        } else if (defaultType % 3 == 2) {
            UIUtils.setDrawableRight(tvDefault, R.mipmap.sort_z_a);
            List<RechargeManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (o1.getPosition() > o2.getPosition()) {
                    return -1;
                } else if (o1.getPosition() == o2.getPosition()) {
                    return 0;
                } else {
                    return 1;
                }
            });
            adapter.setDatas(newData);
        } else {
            UIUtils.setDrawableRight(tvDefault, R.mipmap.sort_default);
            adapter.setDatas(data);
        }
        UIUtils.setDrawableRight(tvRechargeMoney, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvRechargeGiveMoney, R.mipmap.sort_default);
    }

    int nameType = 0;

    @OnClick(R.id.recharge_money)
    public void setNameClick() {
        nameType++;
        if (data == null) {
            return;
        }
        if (nameType % 3 == 1) {
            UIUtils.setDrawableRight(tvRechargeMoney, R.mipmap.sort_a_z);
            List<RechargeManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getRechargeMoney()) > Integer.parseInt(o2.getRechargeMoney())) {
                    return 1;
                } else if (Integer.parseInt(o1.getRechargeMoney()) == Integer.parseInt(o2.getRechargeMoney())) {
                    return 0;
                } else {
                    return -1;
                }
            });
            adapter.setDatas(newData);
        } else if (nameType % 3 == 2) {
            UIUtils.setDrawableRight(tvRechargeMoney, R.mipmap.sort_z_a);
            List<RechargeManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getRechargeMoney()) > Integer.parseInt(o2.getRechargeMoney())) {
                    return -1;
                } else if (Integer.parseInt(o1.getRechargeMoney()) == Integer.parseInt(o2.getRechargeMoney())) {
                    return 0;
                } else {
                    return 1;
                }
            });
            adapter.setDatas(newData);
        } else {
            UIUtils.setDrawableRight(tvRechargeMoney, R.mipmap.sort_default);
        }
        UIUtils.setDrawableRight(tvDefault, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvRechargeGiveMoney, R.mipmap.sort_default);
    }

    int stateType = 0;

    @OnClick(R.id.recharge_give_money)
    public void setStateClick() {
        stateType++;
        if (data == null) {
            return;
        }
        if (stateType % 3 == 1) {
            UIUtils.setDrawableRight(tvRechargeGiveMoney, R.mipmap.sort_a_z);
            List<RechargeManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getGiveMoney()) > Integer.parseInt(o2.getGiveMoney())) {
                    return 1;
                } else if (Integer.parseInt(o1.getGiveMoney()) == Integer.parseInt(o2.getGiveMoney())) {
                    return 0;
                } else {
                    return -1;
                }
            });
            adapter.setDatas(newData);
        } else if (stateType % 3 == 2) {
            UIUtils.setDrawableRight(tvRechargeGiveMoney, R.mipmap.sort_z_a);
            List<RechargeManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getGiveMoney()) > Integer.parseInt(o2.getGiveMoney())) {
                    return -1;
                } else if (Integer.parseInt(o1.getGiveMoney()) == Integer.parseInt(o2.getGiveMoney())) {
                    return 0;
                } else {
                    return 1;
                }
            });
            adapter.setDatas(newData);
        } else {
            UIUtils.setDrawableRight(tvRechargeGiveMoney, R.mipmap.sort_default);
        }
        UIUtils.setDrawableRight(tvRechargeMoney, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvDefault, R.mipmap.sort_default);
    }

    @OnClick(R.id.recharge_manage_select)
    public void setSelectClick() {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_select, null);
        popupWindow = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout linearLayout1 = (LinearLayout) laheiView.findViewById(R.id.linearLayout1);
        LinearLayout linearLayout2 = (LinearLayout) laheiView.findViewById(R.id.linearLayout2);
        TextView tv2 = (TextView) laheiView.findViewById(R.id.tv2);
        linearLayout1.setVisibility(View.GONE);
        tv2.setText("登录名称");
        TextView etEndTime = (TextView) laheiView.findViewById(R.id.pop_endtime);
        etEndTime.setHint("请输入商家登录名称");

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        laheiView.findViewById(R.id.pop_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entTime = etEndTime.getText().toString();
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        popupWindow.setOnDismissListener(() -> {
            backgroundAlpha(1f);
        });
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
    }

    @Override
    public void success(List<RechargeManageBean> rechargeManageBeanList) {
        this.data = rechargeManageBeanList;
        adapter.setDatas(rechargeManageBeanList);

    }

    public void showDeletePop(RechargeManageBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_delete, null);
        laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            laheiPop.dismiss();
        });
        laheiView.findViewById(R.id.pop_delete).setOnClickListener(v -> {
            presenter.deleteRechargeItem(bean.getGuid());
        });
        laheiView.findViewById(R.id.pop_edit).setOnClickListener(v -> {
            Intent intent = new Intent(this, RechargeManageEditActivity.class);
            if (data != null) {
                intent.putExtra(Config.PARAM1, bean);
            }
            startActivityForResult(intent, 101);
        });
        laheiPop.setOutsideTouchable(true);
        laheiPop.setFocusable(true);
        laheiPop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        laheiPop.setOnDismissListener(() -> {
            backgroundAlpha(1f);
        });
        laheiPop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
    }



}
