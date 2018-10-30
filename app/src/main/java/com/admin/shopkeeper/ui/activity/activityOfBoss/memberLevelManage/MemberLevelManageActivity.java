package com.admin.shopkeeper.ui.activity.activityOfBoss.memberLevelManage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.MemberLevelManagerAdapter;
import com.admin.shopkeeper.adapter.MemberManagerAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.MemberInfoBean;
import com.admin.shopkeeper.entity.MemberLevelManageBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.MemberInfo.MemberInfoActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.addMemberLevel.AddMemberLevelActivity;
import com.admin.shopkeeper.utils.Tools;
import com.admin.shopkeeper.utils.UIUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberLevelManageActivity extends BaseActivity<MemberLevelManagePresenter> implements IMemberLevelManageView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.member_level_manage_default)
    TextView tvDefault;
    @BindView(R.id.member_level_manage_name)
    TextView tvName;
    @BindView(R.id.member_level_manage_points)
    TextView tvState;
    @BindView(R.id.member_level_manage_select)
    TextView tvSelect;
    private MemberLevelManagerAdapter adapter;
    private PopupWindow laheiPop;
    List<MemberLevelManageBean> data;
    private PopupWindow popupWindow;

    @Override
    protected void initPresenter() {

        presenter = new MemberLevelManagePresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_level_manage;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("会员等级管理");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._1sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new MemberLevelManagerAdapter(this);
        recyclerView.setAdapter(adapter);
        presenter.getMemberLevelInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.getMemberLevelInfo();
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
                Intent intent = new Intent(this, AddMemberLevelActivity.class);
                if (data != null) {
                    intent.putExtra(Config.PARAM1, data.get(0).getShopName());
                }
                startActivityForResult(intent, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    int defaultType = 0;

    @OnClick(R.id.member_level_manage_default)
    public void setDefaultClick() {
        defaultType++;

        if (data == null) {
            return;
        }
        if (defaultType % 3 == 1) {
            UIUtils.setDrawableRight(tvDefault, R.mipmap.sort_a_z);
            List<MemberLevelManageBean> newData = new ArrayList<>();
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
            List<MemberLevelManageBean> newData = new ArrayList<>();
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
        UIUtils.setDrawableRight(tvName, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvState, R.mipmap.sort_default);
    }

    int nameType = 0;

    @OnClick(R.id.member_level_manage_name)
    public void setNameClick() {
        nameType++;
        if (data == null) {
            return;
        }
        if (nameType % 3 == 1) {
            UIUtils.setDrawableRight(tvName, R.mipmap.sort_a_z);
            List<MemberLevelManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getMaxPoints()) > Integer.parseInt(o2.getMaxPoints())) {
                    return 1;
                } else if (Integer.parseInt(o1.getMaxPoints()) == Integer.parseInt(o2.getMaxPoints())) {
                    return 0;
                } else {
                    return -1;
                }
            });
            adapter.setDatas(newData);
        } else if (nameType % 3 == 2) {
            UIUtils.setDrawableRight(tvName, R.mipmap.sort_z_a);
            List<MemberLevelManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getMaxPoints()) > Integer.parseInt(o2.getMaxPoints())) {
                    return -1;
                } else if (Integer.parseInt(o1.getMaxPoints()) == Integer.parseInt(o2.getMaxPoints())) {
                    return 0;
                } else {
                    return 1;
                }
            });
            adapter.setDatas(newData);
        } else {
            UIUtils.setDrawableRight(tvName, R.mipmap.sort_default);
        }
        UIUtils.setDrawableRight(tvDefault, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvState, R.mipmap.sort_default);
    }

    int stateType = 0;

    @OnClick(R.id.member_level_manage_points)
    public void setStateClick() {
        stateType++;
        if (data == null) {
            return;
        }
        if (stateType % 3 == 1) {
            UIUtils.setDrawableRight(tvState, R.mipmap.sort_a_z);
            List<MemberLevelManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getMinPoints()) > Integer.parseInt(o2.getMinPoints())) {
                    return 1;
                } else if (Integer.parseInt(o1.getMinPoints()) == Integer.parseInt(o2.getMinPoints())) {
                    return 0;
                } else {
                    return -1;
                }
            });
            adapter.setDatas(newData);
        } else if (stateType % 3 == 2) {
            UIUtils.setDrawableRight(tvState, R.mipmap.sort_z_a);
            List<MemberLevelManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getMinPoints()) > Integer.parseInt(o2.getMinPoints())) {
                    return -1;
                } else if (Integer.parseInt(o1.getMinPoints()) == Integer.parseInt(o2.getMinPoints())) {
                    return 0;
                } else {
                    return 1;
                }
            });
            adapter.setDatas(newData);
        } else {
            UIUtils.setDrawableRight(tvState, R.mipmap.sort_default);
        }
        UIUtils.setDrawableRight(tvName, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvDefault, R.mipmap.sort_default);
    }

    @OnClick(R.id.member_level_manage_select)
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
    public void success(List<MemberLevelManageBean> memberLevelManageBeanList) {
        this.data = memberLevelManageBeanList;
        adapter.setDatas(memberLevelManageBeanList);

    }

    public void showDeletePop(MemberLevelManageBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_delete, null);
        laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            laheiPop.dismiss();
        });
        laheiView.findViewById(R.id.pop_delete).setOnClickListener(v -> {
            presenter.deleteMemberLevel(bean.getGuid());
        });
        laheiView.findViewById(R.id.pop_edit).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddMemberLevelActivity.class);
            if (data != null) {
                intent.putExtra(Config.PARAM2, bean);
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
