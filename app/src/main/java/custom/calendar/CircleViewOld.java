package custom.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import util.DimensionUtil;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class CircleViewOld extends View {
    private static final String TAG = CircleViewOld.class.getSimpleName();
    float radius, circleStrokeWidth, padding;
    Paint innerCirclePaint, circlePaint;
    float mLastMotionX, mLastMotionY;
    boolean mFirstTouch;
    Dragging mDragging;
    int mPointerId, mTouchSlop;
    LayoutChangeListener mLayoutChangeListener;

    public CircleViewOld(Context context) {
        this(context, null);
    }

    public CircleViewOld(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleViewOld(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    enum Dragging {
        XDragging,
        YDragging,
        Invalid;
    }

    private void init() {

        radius = DimensionUtil.dp2px(getContext(), 5);
        circleStrokeWidth = DimensionUtil.dp2px(getContext(), 3);
        padding = DimensionUtil.dp2px(getContext(), 2);
        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint.setStyle(Paint.Style.FILL);
        innerCirclePaint.setColor(Color.WHITE);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(circleStrokeWidth);
        circlePaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) ((radius + padding) * 2);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, innerCirclePaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, circlePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFirstTouch = true;
                mPointerId = event.getPointerId(0);
                mLastMotionX = event.getX();
                mLastMotionY = event.getY();
                mLayoutChangeListener.disallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerIndex = event.findPointerIndex(mPointerId);
                float x = event.getX(pointerIndex);
                float y = event.getY(pointerIndex);
                float deltaX = x - mLastMotionX;
                float deltaY = y - mLastMotionY;
                float delta;
                if (mFirstTouch) {
                    //偏移量过小丢弃
                    delta = Math.max(Math.abs(deltaX), Math.abs(deltaY));
                    if (delta > mTouchSlop) {
                        mFirstTouch = false;
                        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
                            mDragging = Dragging.XDragging;
                        } else {
                            mDragging = Dragging.YDragging;
                        }
                    } else {
                        return true;
                    }
                }
                if (mDragging == Dragging.XDragging) {
                    //if (Math.abs(deltaX) > mTouchSlop) {
                        if (mLayoutChangeListener != null && mLayoutChangeListener.change(true, (int) deltaX)) {
                            moveX(deltaX);
                            mLastMotionX = x;
                        }
                   // }
                } else if (mDragging == Dragging.YDragging) {
                    //if (Math.abs(deltaY) > mTouchSlop) {
                        if (mLayoutChangeListener != null && mLayoutChangeListener.change(false, (int) deltaY)) {
                            moveY(deltaY);
                            mLastMotionY = y;
                        }
                    //}
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastMotionX = 0;
                mLastMotionY = 0;
                mFirstTouch = false;
                mDragging = Dragging.Invalid;
                mLayoutChangeListener.disallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }

    private void moveX(float deltaX) {
        Log.i(TAG, "移动X:" + deltaX);
        layout(getLeft() + (int) deltaX, getTop(), getRight() + (int) deltaX, getBottom());
        invalidate();
    }

    private void moveY(float deltaY) {
        Log.i(TAG, "移动Y:" + deltaY);
        layout(getLeft(), getTop() + (int) deltaY, getRight(), getBottom() + (int) deltaY);
        invalidate();
    }

    public void changeLayout(boolean topCircle, int left, int top, int right, int bottom) {
        if (topCircle) {
            left+=padding;
            right = (int) (left + getMeasuredWidth());
            bottom = top + getMeasuredHeight() / 2;
            top = top - getMeasuredHeight() / 2;
        } else {
            left=(int) (right - getMeasuredWidth()-padding);

            top = bottom - getMeasuredHeight() / 2;
            bottom = bottom + getMeasuredHeight() / 2;
        }
        layout(left,top,right,bottom);
        invalidate();
    }

    public void setLayoutChangeListener(LayoutChangeListener mLayoutChangeListener) {
        this.mLayoutChangeListener = mLayoutChangeListener;
    }

    interface LayoutChangeListener {
        boolean change(boolean xChanged, int delta);
        void disallowInterceptTouchEvent(boolean yes);
    }

}
