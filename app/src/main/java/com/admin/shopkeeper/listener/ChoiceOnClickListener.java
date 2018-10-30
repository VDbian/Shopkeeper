package com.admin.shopkeeper.listener;

import android.content.DialogInterface;

public class ChoiceOnClickListener implements DialogInterface.OnClickListener {

    private int which = 0;

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        this.which = which;
    }

    public int getWhich() {
        return which;
    }
}