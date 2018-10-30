package com.admin.shopkeeper.ui.activity.activityOfBoss.shopPermissionEdit;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.ShopPermissionEditAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.PermissionJson;
import com.admin.shopkeeper.entity.PermissionsOfUser;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopPermissionEditActivity extends BaseActivity<ShopPermissionEditPresenter> implements IShopPermissionEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String userId;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    private ShopPermissionEditAdapter adapter;
    private ShopPermissionEditAdapter adapter1;
    private ShopPermissionEditAdapter adapter2;


    @Override
    protected void initPresenter() {
        presenter = new ShopPermissionEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_permission_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("商家权限编辑");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView1.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerView1.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new ShopPermissionEditAdapter(R.layout.item_shop_permission_edit);
        recyclerView1.setAdapter(adapter);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });
        recyclerView2.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());

        adapter1 = new ShopPermissionEditAdapter(R.layout.item_shop_permission_edit);
        recyclerView2.setAdapter(adapter1);

        recyclerView3.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerView3.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter2 = new ShopPermissionEditAdapter(R.layout.item_shop_permission_edit);
        recyclerView3.setAdapter(adapter2);

        Intent intent = getIntent();
        userId = intent.getStringExtra(Config.PARAM1);
        if (!TextUtils.isEmpty(userId)) {
            presenter.getPermission(userId);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_boss, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_sure:
                changePermission();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changePermission() {
        String userID = "";
        String permissionJson = "";
        userID = adapter.getItem(0).getUserID();
        List<PermissionJson.PermissionJsonBase> listJson = new ArrayList<>();
        PermissionJson.PermissionJsonBase permissionJsonBase = new PermissionJson.PermissionJsonBase();
        PermissionJson.PermissionJsonBase permissionJsonBase1 = new PermissionJson.PermissionJsonBase();
        int itemCount = adapter.getData().size();
        String permissionName = "";
        String permissionValue = "";
        for(int i=0 ; i<itemCount ; i++){
            if(!TextUtils.isEmpty(adapter.getItem(i).getPermissionValue().trim())){
                permissionValue += adapter.getItem(i).getPermissionValue()+"^";
                permissionName += adapter.getItem(i).getPermissionName()+"^";
            }
        }
        if (permissionName.length()>1) {
            permissionName = permissionName.substring(0, permissionName.length() - 1);
        }
        if (permissionValue.length()>1) {
            permissionValue = permissionValue.substring(0, permissionValue.length() - 1);
        }
        permissionJsonBase.setPermissionName(permissionName);
        permissionJsonBase.setPermissionValue(permissionValue);
        permissionJsonBase.setPermissionID(adapter.getItem(0).getPermissionID());
        permissionJsonBase.setName(adapter.getItem(0).getName());
        permissionJsonBase.setPermissionUrl(adapter.getItem(0).getPermissionUrl());
        listJson.add(permissionJsonBase);
        int itemCount1 = adapter1.getData().size();
        String permissionName1 = "";
        String permissionValue1 = "";
        for(int i=0 ; i<itemCount1 ; i++){
            if(!TextUtils.isEmpty(adapter1.getItem(i).getPermissionValue().trim())){
                permissionValue1 += adapter1.getItem(i).getPermissionValue()+"^";
                permissionName1 += adapter1.getItem(i).getPermissionName()+"^";
            }

        }
        if (permissionName1.length()>1) {
            permissionName1 = permissionName1.substring(0, permissionName1.length() - 1);
        }
        if (permissionValue1.length()>1) {
            permissionValue1 = permissionValue1.substring(0, permissionValue1.length() - 1);
        }
        permissionJsonBase1.setPermissionName(permissionName1);
        permissionJsonBase1.setPermissionValue(permissionValue1);
        permissionJsonBase1.setPermissionID(adapter1.getItem(0).getPermissionID());
        permissionJsonBase1.setName(adapter1.getItem(0).getName());
        permissionJsonBase1.setPermissionUrl(adapter1.getItem(0).getPermissionUrl());
        listJson.add(permissionJsonBase1);
        permissionJson = new Gson().toJson(listJson);
        Log.d("ttt","permissionJson:"+permissionJson.toString());
        presenter.changeUserPermission(userID ,permissionJson);
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(List<PermissionsOfUser> permissionsOfUserList) {
        if (!permissionsOfUserList.isEmpty()) {
            if (permissionsOfUserList.size() == 1) {
                tv1.setText(permissionsOfUserList.get(0).getName());
                List<PermissionsOfUser> list = null;
                String name[] = permissionsOfUserList.get(0).getPermissionName().split(",");
                String value[] = permissionsOfUserList.get(0).getPermissionValue().split(",");

                for (int i = 0; i < name.length; i++) {
                    list.add(new PermissionsOfUser(permissionsOfUserList.get(0).getGuId(),permissionsOfUserList.get(0).getUserID(),permissionsOfUserList.get(0).getPermissionID(),name[i], value[i],permissionsOfUserList.get(0).getName(),permissionsOfUserList.get(0).getPermissionUrl()));
                }
                if (!list.isEmpty()) {
                    adapter.setNewData(list);
                }
            } else if (permissionsOfUserList.size() == 2) {
                String name1[];
                String value1[];
                String name2[];
                String value2[];
                if(permissionsOfUserList.get(0).getName().equals("结账")){
                    tv1.setText(permissionsOfUserList.get(0).getName());
                    tv2.setText(permissionsOfUserList.get(1).getName());
                     name1 = permissionsOfUserList.get(0).getPermissionName().split(",");
                     value1 = permissionsOfUserList.get(0).getPermissionValue().split(",");
                     name2 = permissionsOfUserList.get(1).getPermissionName().split(",");
                     value2 = permissionsOfUserList.get(1).getPermissionValue().split(",");
                }else{
                    tv1.setText(permissionsOfUserList.get(1).getName());
                    tv2.setText(permissionsOfUserList.get(0).getName());
                     name1 = permissionsOfUserList.get(1).getPermissionName().split(",");
                     value1 = permissionsOfUserList.get(1).getPermissionValue().split(",");
                     name2 = permissionsOfUserList.get(0).getPermissionName().split(",");
                     value2 = permissionsOfUserList.get(0).getPermissionValue().split(",");
                }
                tv2.setVisibility(View.VISIBLE);
                List<PermissionsOfUser> list1 = new ArrayList();
                List<PermissionsOfUser> list2 = new ArrayList();
                for (int i = 0; i < name1.length; i++) {
                    list1.add(new PermissionsOfUser(permissionsOfUserList.get(0).getGuId(),permissionsOfUserList.get(0).getUserID(),permissionsOfUserList.get(0).getPermissionID(),name1[i], value1[i],permissionsOfUserList.get(0).getName(),permissionsOfUserList.get(0).getPermissionUrl()));

                }
                if(!list1.isEmpty()){
                    adapter.setNewData(list1);
                }
                adapter.setNewData(list1);
                for (int i = 0; i < name2.length; i++) {
                    list2.add(new PermissionsOfUser(permissionsOfUserList.get(1).getGuId(),permissionsOfUserList.get(1).getUserID(),permissionsOfUserList.get(1).getPermissionID(),name2[i], value2[i],permissionsOfUserList.get(1).getName(),permissionsOfUserList.get(1).getPermissionUrl()));
                }
                if(!list2.isEmpty()){
                    recyclerView2.setVisibility(View.VISIBLE);
                    adapter1.setNewData(list2);
                }
            } /*else if (permissionsOfUserList.size() == 3) {
                String name1[];
                String value1[];
                String name2[];
                String value2[];
                String name3[];
                String value3[];
                if(permissionsOfUserList.get(0).getName().equals("结账")){
                    tv1.setText(permissionsOfUserList.get(0).getName());
                    tv2.setText(permissionsOfUserList.get(1).getName());
                    tv3.setText(permissionsOfUserList.get(2).getName());
                    name1 = permissionsOfUserList.get(0).getPermissionName().split(",");
                    value1 = permissionsOfUserList.get(0).getPermissionValue().split(",");
                    name2 = permissionsOfUserList.get(1).getPermissionName().split(",");
                    value2 = permissionsOfUserList.get(1).getPermissionValue().split(",");
                    name3 = permissionsOfUserList.get(2).getPermissionName().split(",");
                    value3 = permissionsOfUserList.get(2).getPermissionValue().split(",");
                }else{
                    tv1.setText(permissionsOfUserList.get(1).getName());
                    tv2.setText(permissionsOfUserList.get(0).getName());
                    tv3.setText(permissionsOfUserList.get(2).getName());
                    name1 = permissionsOfUserList.get(1).getPermissionName().split(",");
                    value1 = permissionsOfUserList.get(1).getPermissionValue().split(",");
                    name2 = permissionsOfUserList.get(0).getPermissionName().split(",");
                    value2 = permissionsOfUserList.get(0).getPermissionValue().split(",");
                    name3 = permissionsOfUserList.get(2).getPermissionName().split(",");
                    value3 = permissionsOfUserList.get(2).getPermissionValue().split(",");
                }
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                List<PermissionsOfUserOfItem> list1 = new ArrayList();
                List<PermissionsOfUserOfItem> list2 = new ArrayList();
                List<PermissionsOfUserOfItem> list3 = new ArrayList();
                for (int i = 0; i < name1.length; i++) {
                    list1.add(new PermissionsOfUserOfItem(name1[i], value1[i]));
                }
                if(!list1.isEmpty()){
                    adapter = new ShopPermissionEditAdapter(R.layout.item_shop_permission_edit, list1);
                    recyclerView1.setAdapter(adapter);
                }
                for (int i = 0; i < name2.length; i++) {
                    list2.add(new PermissionsOfUserOfItem(name2[i],value2[i]));
                }
                if(!list2.isEmpty()){
                    adapter1 = new ShopPermissionEditAdapter(R.layout.item_shop_permission_edit, list2);
                    recyclerView2.setVisibility(View.VISIBLE);
                }
                recyclerView2.setAdapter(adapter1);
                for (int i = 0; i < name3.length; i++) {
                    list3.add(new PermissionsOfUserOfItem(name3[i],value3[i]));
                }
                if(!list3.isEmpty()){
                    adapter2 = new ShopPermissionEditAdapter(R.layout.item_shop_permission_edit, list3);
                    recyclerView3.setVisibility(View.VISIBLE);
                }
                recyclerView3.setAdapter(adapter2);
            }*/
        }
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
    }
}
