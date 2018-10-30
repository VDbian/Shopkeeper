package com.admin.shopkeeper.ui.activity.queue;


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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.QueueAdapter;
import com.admin.shopkeeper.adapter.WeightAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.SingleSelectDialog;
import com.admin.shopkeeper.entity.DeskTypeBean;
import com.admin.shopkeeper.entity.QueueBean;
import com.admin.shopkeeper.entity.WaiterBussinessBean;
import com.admin.shopkeeper.entity.WeightBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.weight.IWeightView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.weight.WeightPresenter;
import com.admin.shopkeeper.ui.activity.activityOfBoss.weightEdit.WeightEditActivity;
import com.admin.shopkeeper.ui.activity.queueedit.QueueEditActivity;
import com.admin.shopkeeper.utils.Tools;
import com.admin.shopkeeper.utils.UIUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class QueueActivity extends BaseActivity<QueuePresenter> implements IQueueView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.desk_no)
    TextView tvNo;

    private QueueAdapter adapter;
    private PopupWindow laheiPop;
    private SpeechSynthesizer speechSynthesizer;

    List<QueueBean> selectList = new ArrayList<>();

    @Override
    protected void initPresenter() {
        presenter = new QueuePresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_queue;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("排号");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        speechSynthesizer = SpeechSynthesizer.createSynthesizer(this, new InitListener() {
            @Override
            public void onInit(int i) {

            }
        });

        speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "aisjying");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QueueAdapter(R.layout.item_queue);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            showDeletePop(adapter.getData().get(position));
        });

        presenter.getData();
    }

    public void showDeletePop(QueueBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_queue, null);
        laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        TextView textView = (TextView) laheiView.findViewById(R.id.pop_name);
        textView.setText(bean.getNo());

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            laheiPop.dismiss();
        });

        laheiView.findViewById(R.id.pop_call).setOnClickListener(v -> {
            String speakText = "请 " + bean.getNo() + " 号就餐";
            speechSynthesizer.startSpeaking(speakText, null);
            laheiPop.dismiss();
        });
        laheiView.findViewById(R.id.pop_delete).setOnClickListener(v -> {
            presenter.delete(bean);
        });
        laheiView.findViewById(R.id.pop_edit).setOnClickListener(v -> {
            Intent intent = new Intent(this, QueueEditActivity.class);
            intent.putExtra("bean", bean);
            startActivityForResult(intent, 101);
            laheiPop.dismiss();
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

    @OnClick(R.id.desk_select)
    public void selectClick() {
        if (deskTypeList == null || deskTypeList.size() == 0) {
            presenter.getTableType();
        } else {
            showTypeDialog();
        }
    }

    private void showTypeDialog() {
        if (deskTypeList == null || deskTypeList.size() == 0) {
            return;
        }

        List<String> names = new ArrayList<>();
        for (int i = 0; i < deskTypeList.size(); i++) {
            names.add(deskTypeList.get(i).getName());
        }

        SingleSelectDialog.Builder builder = new SingleSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("筛选桌位类别");
        builder.setReasons(names);
        builder.setButtonClick(new SingleSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, int position) {
                DeskTypeBean typeBean = deskTypeList.get(position);
                selectList.clear();
                for (QueueBean queueBean : queueList) {
                    if (queueBean.getTableName().equals(typeBean.getName())) {
                        selectList.add(queueBean);
                    }
                }
                adapter.setNewData(selectList);
            }

            @Override
            public void onCancel() {
                adapter.setNewData(queueList);
            }
        });
        builder.creater().show();
    }

    int noType = 0;

    @OnClick(R.id.desk_no)
    public void countClick() {
        List<QueueBean> beanList = adapter.getData();
        noType++;
        if (noType % 3 == 1) {
            UIUtils.setDrawableRight(tvNo, R.mipmap.sort_a_z);

            List<QueueBean> newData = new ArrayList<>();
            newData.addAll(beanList);
            Collections.sort(newData, (o1, o2) -> {
                int i = o1.getTypes().compareTo(o2.getTypes());
                if (i == 0) {
                    if (o1.getCallNo() > o2.getCallNo()) {
                        return 1;
                    } else if (o1.getCallNo() < o2.getCallNo()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } else {
                    return i;
                }
            });
            adapter.setNewData(newData);
        } else if (noType % 3 == 2) {
            UIUtils.setDrawableRight(tvNo, R.mipmap.sort_z_a);

            List<QueueBean> newData = new ArrayList<>();
            newData.addAll(beanList);
            Collections.sort(newData, (o1, o2) -> {
                int i = o2.getTypes().compareTo(o1.getTypes());
                if (i == 0) {
                    if (o2.getCallNo() > o1.getCallNo()) {
                        return 1;
                    } else if (o2.getCallNo() < o1.getCallNo()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } else {
                    return i;
                }
            });
            adapter.setNewData(newData);
        } else {
            UIUtils.setDrawableRight(tvNo, R.mipmap.sort_default);
            if (beanList.size() == selectList.size()) {
                adapter.setNewData(selectList);
            } else {
                adapter.setNewData(queueList);
            }
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
                startActivityForResult(QueueEditActivity.class, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.getData();
        }
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        if (laheiPop != null) {
            laheiPop.dismiss();
        }
    }

    List<QueueBean> queueList = new ArrayList<>();

    @Override
    public void success(List<QueueBean> datas) {
        queueList.clear();
        queueList.addAll(datas);
        adapter.setNewData(queueList);
    }

    List<DeskTypeBean> deskTypeList = new ArrayList<>();

    @Override
    public void tableTypeSuccess(List<DeskTypeBean> deskTypeBeen) {
        this.deskTypeList = deskTypeBeen;
        showTypeDialog();
    }
}
