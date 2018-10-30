package com.admin.shopkeeper.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.admin.shopkeeper.R;

public class QuickIndexBar extends View {
    public static final String[] LETTERS = new String[]{"A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static final int[] INDEX = new int[26];

    private Paint paint;
    private int mCellWidth;
    private int mCellHeight;
    private float mTextHeight;
    private int currentIndex = -1;
    private Context context;
    private OnLetterChangeListener onLetterChangeListener;

    public OnLetterChangeListener getOnLetterChangeListener() {
        return onLetterChangeListener;
    }

    public void setOnLetterChangeListener(OnLetterChangeListener onLetterChangeListener) {
        this.onLetterChangeListener = onLetterChangeListener;
    }

    public interface OnLetterChangeListener {
        void onLetterChange(String letter);

        void onLetterIndex(int position);

        //手指抬起
        void onReset();
    }


    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        paint = new Paint();
//        paint.setColor(Color.);
        paint.setColor(Color.BLACK);
        paint.setTextSize(24);
        //消除锯齿
        paint.setAntiAlias(true);


        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        mTextHeight = (float) Math.ceil(fontMetrics.descent - fontMetrics.ascent);  //1.1---2   2.1--3

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCellWidth = getMeasuredWidth();
        mCellHeight = getMeasuredHeight() / LETTERS.length;
    }

    /**
     * 绘制控件
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        字.画字();
        for (int i = 0; i < LETTERS.length; i++) {
            String letter = LETTERS[i];
            float mTextWidth = paint.measureText(letter);
            float x = (mCellWidth - mTextWidth) * 0.5f;
            float y = (mCellHeight + mTextHeight) * 0.5f + mCellHeight * i;

            if (i == currentIndex) {
                paint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
            } else {
                paint.setColor(Color.BLACK);
            }

            canvas.drawText(letter, x, y, paint);
        }
    }


    /**
     * 处理 按下 移动 手指抬起
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("我按下了!!");
                int downY = (int) event.getY();
                //获取当前索引
                currentIndex = downY / mCellHeight;
                if (currentIndex < 0 || currentIndex > LETTERS.length - 1) {

                } else {
//                   ToastUtil.showToast(getContext(),LETTERS[currentIndex]);
                    if (onLetterChangeListener != null) {
                        onLetterChangeListener.onLetterChange(LETTERS[currentIndex]);
                        onLetterChangeListener.onLetterIndex(INDEX[currentIndex]);
                    }
                }
                //重新绘制
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("我移动了!!");
                int moveY = (int) event.getY();
                //获取当前索引
                currentIndex = moveY / mCellHeight;
                if (currentIndex < 0 || currentIndex > LETTERS.length - 1) {

                } else {
//                    ToastUtil.showToast(getContext(),LETTERS[currentIndex]);
                    if (onLetterChangeListener != null) {
                        onLetterChangeListener.onLetterChange(LETTERS[currentIndex]);
                        onLetterChangeListener.onLetterIndex(INDEX[currentIndex]);
                    }
                }
                //重新绘制
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("我手指抬起了!!");
                currentIndex = -1;
                //手动刷新
                invalidate();
                //表示手指抬起了
                if (onLetterChangeListener != null) {
                    onLetterChangeListener.onReset();
                }


                break;
        }


        // 为了 能够接受  move+up事件
        return true;
    }
}