package com.admin.shopkeeper.ui.activity.queueedit;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.SingleSelectDialog;
import com.admin.shopkeeper.entity.DeskTypeBean;
import com.admin.shopkeeper.entity.QueueBean;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.entity.TableEntity;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class QueueEditActivity extends BaseActivity<QueueEditPresenter> implements IQueueEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_area)
    TextView tvArea;
    @BindView(R.id.edit_table)
    TextView tvTable;
    @BindView(R.id.edit_room)
    TextView tvRoom;
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.ll_room)
    LinearLayout llRoom;
    @BindView(R.id.ll_no)
    LinearLayout llNo;

    private QueueBean bean;

    @Override
    protected void initPresenter() {
        presenter = new QueueEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_queue_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);

        bean = (QueueBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            toolbar.setTitle("绑定桌位");
            llArea.setVisibility(View.GONE);
            presenter.getTabelDB();
            presenter.getTable();
        } else {
            toolbar.setTitle("新增排队");
            llNo.setVisibility(View.GONE);
            llRoom.setVisibility(View.GONE);
        }
        setSupportActionBar(toolbar);

        presenter.getTableType();
        //presenter.getTable();
    }

    @OnClick(R.id.edit_area)
    public void areaClick() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < deskTypeBeen.size(); i++) {
            names.add(deskTypeBeen.get(i).getName());
        }

        SingleSelectDialog.Builder builder = new SingleSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("桌位类别");
        builder.setReasons(names);
        builder.setButtonClick(new SingleSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, int position) {
                selectDeskType = deskTypeBeen.get(position);
                tvArea.setText(text);
            }

            @Override
            public void onCancel() {

            }
        });
        builder.creater().show();
    }

    @OnClick(R.id.edit_table)
    public void tableClick() {
        if (tableEntities == null) {
            return;
        }
        selectTableList.clear();
        List<String> names = new ArrayList<>();
        for (TableEntity tableEntity : tableEntities) {
            if (selectRoom != null) {
                if (tableEntity.getRoomID().equals(selectRoom.getId())) {
                    names.add(tableEntity.getTableName());
                    selectTableList.add(tableEntity);
                }
            } else {
                names.add(tableEntity.getTableName());
                selectTableList.add(tableEntity);
            }
        }

        SingleSelectDialog.Builder builder = new SingleSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("房间");
        builder.setReasons(names);
        builder.setButtonClick(new SingleSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, int position) {
                selectDeskBean = selectTableList.get(position);
                tvTable.setText(selectDeskBean.getTableName());
            }

            @Override
            public void onCancel() {

            }
        });
        builder.creater().show();
    }

    RoomEntity selectRoom;

    @OnClick(R.id.ll_room)
    public void roomClick() {
        if (roomEntities == null) {
            return;
        }
        List<String> names = new ArrayList<>();
        for (RoomEntity roomEntity : roomEntities) {
            names.add(roomEntity.getName());
        }

        SingleSelectDialog.Builder builder = new SingleSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("桌位");
        builder.setReasons(names);
        builder.setButtonClick(new SingleSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, int position) {
                selectRoom = roomEntities.get(position);
                tvRoom.setText(selectRoom.getName());
            }

            @Override
            public void onCancel() {

            }
        });
        builder.creater().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_submit:
                submit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submit() {
        if (bean == null) {

            if (selectDeskType == null) {
                showToast("请选择桌位类型");
                return;
            }
            presenter.add(selectDeskType);
        } else {
            if (selectDeskType == null) {
                showToast("请选择桌位类型");
                return;
            }

            if (selectDeskBean == null) {
                showToast("请选择桌位");
                return;
            }

            presenter.bind(bean, selectDeskType, selectDeskBean);
        }
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        setResult(RESULT_OK);
        finish();
    }

    List<DeskTypeBean> deskTypeBeen;
    DeskTypeBean selectDeskType = null;

    @Override
    public void tableTypeSuccess(List<DeskTypeBean> deskTypeBeen) {
        this.deskTypeBeen = deskTypeBeen;

        if (bean != null) {
            for (int i = 0; i < deskTypeBeen.size(); i++) {
                if (bean.getTableName().equals(deskTypeBeen.get(i).getName())) {
                    selectDeskType = deskTypeBeen.get(i);
                    tvArea.setText(selectDeskType.getName());
                }
            }
        }
    }

    List<TableEntity> tableEntities = new ArrayList<>();
    List<TableEntity> selectTableList = new ArrayList<>();
    TableEntity selectDeskBean;

    @Override
    public void tableSuccess(List<TableEntity> deskBeen) {
        tableEntities.clear();
        for (int i = 0; i < deskBeen.size(); i++) {
            if (deskBeen.get(i).getOpen().equals("0")) {
                tableEntities.add(deskBeen.get(i));
            }
        }
    }

    List<RoomEntity> roomEntities = new ArrayList<>();

    @Override
    public void showDB(List<RoomEntity> roomEntities) {
        this.roomEntities.addAll(roomEntities);
    }
}
