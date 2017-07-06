package com.lcp.customviewtest;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by linchenpeng on 2017/6/29.
 */

public class CustomButton extends android.support.v7.widget.AppCompatButton {

    private final static String TAG = CustomButton.class.getSimpleName();
    OnCustomTouchListener onCustomTouchListener;
    long mEventTime;
    Point mLastPoint, mTempPoint;
    Runnable checkRunnable;
    int mTouchSlop;


    public CustomButton(Context context) {
        super(context);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        checkRunnable = new Runnable() {
            @Override
            public void run() {
                if (onCustomTouchListener != null) {
                    Log.i(TAG, "TouchSlop:" + mTouchSlop +
                            "," + SystemClock.uptimeMillis() + "----" + mEventTime + "====" + (SystemClock.uptimeMillis() - mEventTime));
                    Log.i(TAG, mTempPoint.x + "===" + mLastPoint.x + ",,," + mTempPoint.y + "===" + mLastPoint.y);
                    if (mTempPoint.equals(mLastPoint) && SystemClock.uptimeMillis() - mEventTime >= 500)
                        onCustomTouchListener.onTouch();
                    else
                        onCustomTouchListener.onCancel();
                }
                postDelayed(checkRunnable, 30);
            }
        };
    }

    public void setOnCustomTouchListener(OnCustomTouchListener onCustomTouchListener) {
        this.onCustomTouchListener = onCustomTouchListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTempPoint = new Point(event.getRawX(), event.getRawY());
        Log.i(TAG, "action:" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastPoint = new Point(event.getRawX(), event.getRawY());
                mEventTime = event.getDownTime();
                post(checkRunnable);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mLastPoint.equals(mTempPoint)) {
                    //不相等说明滑动了，那就重新计算时间
                    mEventTime = event.getEventTime();
                }
                mLastPoint = mTempPoint;
                break;
            case MotionEvent.ACTION_UP:
                removeCallbacks(checkRunnable);
                if (onCustomTouchListener != null)
                    onCustomTouchListener.onCancel();
                break;
        }
        return true;
    }

    interface OnCustomTouchListener {
        void onTouch();

        void onCancel();
    }

    class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(float x, float y) {
            this((int) x, (int) y);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj instanceof Point) {
                Point temp = (Point) obj;
                return Math.abs(x - temp.x) < 5  && Math.abs(y - temp.y) < 5;
            }
            return false;
        }
    }
}
