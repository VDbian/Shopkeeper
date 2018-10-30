package com.admin.shopkeeper.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.DialogSetFoodAdapter;
import com.admin.shopkeeper.adapter.DialogSetFoodTypeAdapter;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;

import java.util.ArrayList;
import java.util.List;

public class MySpinner extends android.support.v7.widget.AppCompatButton {
    private List<Integer> list = new ArrayList<Integer>();
    private OnButtonClick buttonClick;

    public void setButtonClick(OnButtonClick buttonClick) {
        this.buttonClick = buttonClick;
    }

    public MySpinner(Context context) {
        super(context);
    }

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text + "    ▼", type);
    }

    public void initContent(final List<FoodBean> foods) {
        if (foods == null) {
            setText("请选择", null);
        } else {
//            setText(null, null);
        }
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                final SpinnerDialog dialog = new SpinnerDialog(getContext());
                TextView textView = (TextView) dialog.findViewById(R.id.over_text);
                textView.setText("完成");
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (buttonClick != null) {
                            List<FoodBean> list = new ArrayList<>();
                            for (FoodBean food : foods) {
                                if (food.isCheck()) {
                                    list.add(food);
                                }
                            }
                            buttonClick.onSure(list);
                        }
                        dialog.cancel();
                    }
                });
                dialog.show();
                ListView listView = (ListView) dialog.findViewById(R.id.listview);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                DialogSetFoodAdapter adapter = new DialogSetFoodAdapter(getContext(), foods);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        FoodBean bean = foods.get(position);
                        CheckedTextView checktv = (CheckedTextView) view.findViewById(R.id.text1);
                        if (bean.getProductName().equals("全选")) {
                            boolean check = bean.isCheck();
                            for (FoodBean food : foods) {
                                food.setCheck(!check);
                            }
                        } else {
                            if (checktv.isChecked()) {
                                bean.setCheck(false);
                                checktv.setChecked(false);
                            } else {
                                bean.setCheck(true);
                                checktv.setChecked(true);
                            }
                        }

                        Integer object = Integer.valueOf(position);
                        if (list.contains(object)) {
                            list.remove(object);
                        } else {
                            list.add(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        };
        super.setOnClickListener(listener);
    }

    public void initTypeContent(final List<MenuTypeEntity> menuTypeEntities) {
        if (menuTypeEntities == null) {
            setText("请选择", null);
        } else {
//            setText(null, null);
        }
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                final SpinnerDialog dialog = new SpinnerDialog(getContext());
                TextView textView = (TextView) dialog.findViewById(R.id.over_text);
                textView.setText("完成");
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (buttonClick != null) {
                            List<MenuTypeEntity> list = new ArrayList<>();
                            for (MenuTypeEntity food : menuTypeEntities) {
                                if (food.isCheck()) {
                                    list.add(food);
                                }
                            }
                            buttonClick.onSureTypes(list);
                        }
                        dialog.cancel();
                    }
                });
                dialog.show();
                ListView listView = (ListView) dialog.findViewById(R.id.listview);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                DialogSetFoodTypeAdapter adapter = new DialogSetFoodTypeAdapter(getContext(), menuTypeEntities);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        MenuTypeEntity bean = menuTypeEntities.get(position);
                        CheckedTextView checktv = (CheckedTextView) view.findViewById(R.id.text1);
                        if (bean.getProductTypeName().equals("全选")) {
                            boolean check = bean.isCheck();
                            for (MenuTypeEntity food : menuTypeEntities) {
                                food.setCheck(!check);
                            }
                        } else {
                            if (checktv.isChecked()) {
                                bean.setCheck(false);
                                checktv.setChecked(false);
                            } else {
                                bean.setCheck(true);
                                checktv.setChecked(true);
                            }
                        }

                        Integer object = Integer.valueOf(position);
                        if (list.contains(object)) {
                            list.remove(object);
                        } else {
                            list.add(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        };
        super.setOnClickListener(listener);
    }

    private class SpinnerDialog extends Dialog {
        public SpinnerDialog(Context context) {
            super(context, R.style.OrderDialogStyle);
            setContentView(R.layout.spinner);
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.CENTER;
            DisplayMetrics dm = new DisplayMetrics();
            getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            int screenHeigh = dm.heightPixels;
            params.height = (int) (screenHeigh * 0.8);
            params.width = (int) (screenWidth * 0.8);
            window.setAttributes(params);
        }
    }

    public interface OnButtonClick {
        void onSure(List<FoodBean> list);

        void onSureTypes(List<MenuTypeEntity> typeEntityList);
    }
}
