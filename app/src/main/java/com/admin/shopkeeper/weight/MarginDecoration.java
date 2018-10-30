package com.admin.shopkeeper.weight;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.admin.shopkeeper.R;

public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MarginDecoration(Context context, @DimenRes int marginSize) {
        margin = context.getResources().getDimensionPixelSize(marginSize);
    }

    @Override
    public void getItemOffsets(
            Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
    }
}