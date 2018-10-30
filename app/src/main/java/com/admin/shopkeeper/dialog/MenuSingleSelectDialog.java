package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.SingleDialogAdapter;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.entity.TableEntity;
import com.admin.shopkeeper.weight.MarginDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class MenuSingleSelectDialog extends AppCompatDialog {

    public MenuSingleSelectDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private MenuSingleSelectDialog dialog;
        private Context context;
        private int theme;
        SingleDialogAdapter tableAdapter;

        private String title;
        private List<TableEntity> tables;
        private List<RoomEntity> rooms;
        private RoomEntity selectRoom;

        public List<TableEntity> getTables() {
            return tables;
        }

        public void setTables(List<TableEntity> tables) {
            this.tables = tables;
        }

        public List<RoomEntity> getRooms() {
            return rooms;
        }

        public void setRooms(List<RoomEntity> rooms) {
            this.rooms = rooms;
        }

        private List<String> getTableNames(RoomEntity roomEntity) {
            List<String> names = new ArrayList<>();
            for (TableEntity tableEntity : tables) {
                if (tableEntity.getRoomID().equals(roomEntity.getId())) {
                    names.add(tableEntity.getTableName());
                }
            }
            return names;
        }

        private List<TableEntity> getTableDatas(RoomEntity roomEntity) {
            List<TableEntity> names = new ArrayList<>();
            for (TableEntity tableEntity : tables) {
                if (tableEntity.getRoomID().equals(roomEntity.getId())) {
                    names.add(tableEntity);
                }
            }
            return names;
        }

        private List<String> getRoomNames() {
            List<String> names = new ArrayList<>();
            for (RoomEntity roomEntity : rooms) {
                names.add(roomEntity.getName());
            }
            return names;
        }

        private OnButtonClick buttonClick;

        public void setButtonClick(OnButtonClick buttonClick) {
            this.buttonClick = buttonClick;
        }

        public Builder(Context context, int theme) {
            this.context = context;
            this.theme = theme;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public MenuSingleSelectDialog creater() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_menu_single, null);
            dialog = new MenuSingleSelectDialog(context, theme, view);

            Button oneBtn = (Button) view.findViewById(R.id.OneBtn);
            TextView titletv = (TextView) view.findViewById(R.id.dialog_title);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scrollView);
            RecyclerView menuList = (RecyclerView) view.findViewById(R.id.recyclerView);

            titletv.setText(title);
            selectRoom = rooms.get(0);

            oneBtn.setOnClickListener(v -> {
                if (buttonClick != null) {
                    buttonClick.onCancel();
                }
                dismiss();
            });

            menuList.setLayoutManager(new LinearLayoutManager(context));
            SingleDialogAdapter menuAdapter = new SingleDialogAdapter(R.layout.item_muti, getRoomNames());
            menuList.setHasFixedSize(true);
            menuList.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
            menuList.setAdapter(menuAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            tableAdapter = new SingleDialogAdapter(R.layout.item_muti, getTableNames(selectRoom));
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
            recyclerView.setAdapter(tableAdapter);

            menuAdapter.setOnItemClickListener((adapter, view1, position) -> {
                selectRoom = rooms.get(position);
                tableAdapter.setNewData(getTableNames(selectRoom));
            });

            tableAdapter.setOnItemClickListener((adapter, view1, position) -> {
                if (buttonClick != null) {
                    buttonClick.onOk(getTableDatas(selectRoom).get(position), position);
                }
                dismiss();
            });

            return dialog;
        }


        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    public interface OnButtonClick {
        void onOk(TableEntity tableEntity, int position);

        void onCancel();
    }
}
