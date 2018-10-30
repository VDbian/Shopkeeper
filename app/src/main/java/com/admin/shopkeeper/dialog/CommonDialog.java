package com.admin.shopkeeper.dialog;

import android.content.Context;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.CommonDialogEntity;


/**
 * Created by admin on 2017/6/9.
 */

public class CommonDialog extends AppCompatDialog {

    private CommonDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private CommonDialog dialog;
        private Context context;
        private int theme;

        private OnButtonClick buttonClick;

        public OnButtonClick getButtonClick() {
            return buttonClick;
        }

        public void setButtonClick(OnButtonClick buttonClick) {
            this.buttonClick = buttonClick;
        }

        public CommonDialogEntity getEntity() {
            return entity;
        }

        public void setEntity(CommonDialogEntity entity) {
            this.entity = entity;
        }

        private CommonDialogEntity entity;

        public Builder(Context context, int theme) {
            this.context = context;
            this.theme = theme;
        }

        public CommonDialog creater() {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_common, null);
            dialog = new CommonDialog(context, theme, view);

            AppCompatImageButton tipBtn = (AppCompatImageButton) view.findViewById(R.id.tipBtn);
            tipBtn.setOnClickListener(v -> {
                dismiss();
            });

            AppCompatTextView title = (AppCompatTextView) view.findViewById(R.id.foodName);
            if (!TextUtils.isEmpty(entity.getTitle())) {
                title.setText(entity.getTitle());
            }
            ConstraintLayout currLayout = (ConstraintLayout) view.findViewById(R.id.currLayout);
            AppCompatTextView currText = (AppCompatTextView) view.findViewById(R.id.currText);
            if (TextUtils.isEmpty(entity.getCurr())) {
                currLayout.setVisibility(View.GONE);
            } else {
                currText.setText(entity.getCurr());
            }

            ConstraintLayout targetLayout = (ConstraintLayout) view.findViewById(R.id.targetLayout);
            AppCompatEditText targetText = (AppCompatEditText) view.findViewById(R.id.targetText);

            if (!entity.isShowTarget()) {
                targetLayout.setVisibility(View.GONE);
            } else {
                targetText.setText(entity.getTarget());
            }

            ConstraintLayout peopleLayout = (ConstraintLayout) view.findViewById(R.id.peopleLayout);
            AppCompatEditText peopleText = (AppCompatEditText) view.findViewById(R.id.peopleText);


            if (!entity.isShowPeople()) {
                peopleLayout.setVisibility(View.GONE);
            } else {
                peopleText.setText((entity.getPeopleNum() > 0) ? String.valueOf(entity.getPeopleNum()) : "");
            }

            ConstraintLayout canJuLayout = (ConstraintLayout) view.findViewById(R.id.canJuLayout);
            AppCompatEditText canJuText = (AppCompatEditText) view.findViewById(R.id.canJuText);


            if (!entity.isShowCanJu()) {
                canJuLayout.setVisibility(View.GONE);
            } else {
                canJuText.setText((entity.getCanJuNum() > 0) ? String.valueOf(entity.getCanJuNum()) : "");
            }

            AppCompatButton oneBtn = (AppCompatButton) view.findViewById(R.id.OneBtn);
            AppCompatButton leftBtn = (AppCompatButton) view.findViewById(R.id.leftBtn);
            AppCompatButton rightBtn = (AppCompatButton) view.findViewById(R.id.rightBtn);


            // 获取焦点自动qin清除内容
            peopleText.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    peopleText.setText("");
                }
            });

            //内容监控
            peopleText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    canJuText.setText(s);

                    if (!TextUtils.isEmpty(canJuText.getText()) && !TextUtils.isEmpty(peopleText.getText())) {

                        if (Integer.parseInt(canJuText.getText().toString().trim()) > 0
                                && Integer.parseInt(peopleText.getText().toString().trim()) > 0) {
                            leftBtn.setEnabled(true);
                            rightBtn.setEnabled(true);
                            oneBtn.setEnabled(true);
                        } else {
                            leftBtn.setEnabled(false);
                            rightBtn.setEnabled(false);
                            oneBtn.setEnabled(false);
                        }

                    } else {
                        leftBtn.setEnabled(false);
                        rightBtn.setEnabled(false);
                        oneBtn.setEnabled(false);
                    }
                }
            });
            canJuText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(canJuText.getText()) && !TextUtils.isEmpty(peopleText.getText())) {

                        if (Integer.parseInt(canJuText.getText().toString().trim()) > 0
                                && Integer.parseInt(peopleText.getText().toString().trim()) > 0) {
                            leftBtn.setEnabled(true);
                            rightBtn.setEnabled(true);
                        } else {
                            leftBtn.setEnabled(false);
                            rightBtn.setEnabled(false);
                        }

                    } else {
                        leftBtn.setEnabled(false);
                        rightBtn.setEnabled(false);
                    }
                }
            });

            if (entity.isMoreBtn()) {
                oneBtn.setVisibility(View.GONE);
                leftBtn.setText(entity.getLeftBtnText());
                rightBtn.setText(entity.getRightBtnText());
            } else {
                leftBtn.setVisibility(View.GONE);
                rightBtn.setVisibility(View.GONE);
                oneBtn.setText(entity.getOneBtnText());
            }

            oneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    entity.setCanJuNum(TextUtils.isEmpty(canJuText.getText()) ? 0 : Integer.parseInt(canJuText.getText().toString().trim()));
                    entity.setTarget(targetText.getText().toString());
                    entity.setPeopleNum(TextUtils.isEmpty(peopleText.getText()) ? 0 : Integer.parseInt(peopleText.getText().toString().trim()));
                    if (buttonClick != null) {
                        buttonClick.onOneClick(entity);
                    }

                }
            });
            leftBtn.setOnClickListener(v -> {
                entity.setCanJuNum(TextUtils.isEmpty(canJuText.getText()) ? 0 : Integer.parseInt(canJuText.getText().toString().trim()));
                entity.setTarget(targetText.getText().toString());
                entity.setPeopleNum(TextUtils.isEmpty(peopleText.getText()) ? 0 : Integer.parseInt(peopleText.getText().toString().trim()));
                if (buttonClick != null) {
                    buttonClick.onLeftClick(entity);
                }
            });
            rightBtn.setOnClickListener(v -> {
                entity.setCanJuNum(TextUtils.isEmpty(canJuText.getText()) ? 0 : Integer.parseInt(canJuText.getText().toString().trim()));
                entity.setTarget(targetText.getText().toString());
                entity.setPeopleNum(TextUtils.isEmpty(peopleText.getText()) ? 0 : Integer.parseInt(peopleText.getText().toString().trim()));
                if (buttonClick != null) {
                    buttonClick.onRightClick(entity);
                }
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
        void onLeftClick(CommonDialogEntity entity);

        void onRightClick(CommonDialogEntity entity);

        void onOneClick(CommonDialogEntity entity);
    }


}
