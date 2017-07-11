package custom.calendar;

import android.content.Context;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class CircleTop extends CircleView {
    private static final String TAG = CircleTop.class.getSimpleName();

    public CircleTop(Context context) {
        super(context);
    }

    public CircleTop(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleTop(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        scrollRunnable = new Runnable() {
            @Override
            public void run() {
                if (onCircleTouchListener != null) {
                    // TODO: 2017/6/29 //首先应该判断方框的大小是否小等于最小高度，如果是，则不向下滑动了
                    if (!onCircleTouchListener.minHeight()&&mTempPoint.equals(mLastPoint) && SystemClock.uptimeMillis() - mEventTime >= 500) {
                        int direction = scrollDirection();
                        if (direction != DIRECTION_INVALID) {
                            boolean canScroll = onCircleTouchListener.onScrollVertical(direction == DIRECTION_UP);
                            if (canScroll && onCircleTouchListener != null) {
                                Point point = onCircleTouchListener.reLayoutTop(0, direction == DIRECTION_UP ? -30 : 30);
                                int transitionY = (int) (getTranslationY() + point.y);
                                setTranslationY(transitionY);
                            }
                        }
                    }
                }
                postDelayed(scrollRunnable, 30);
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int tempY = (int) event.getRawY();
        mTempPoint = new CustomPoint(event.getRawX(), tempY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastPoint = new CustomPoint(event.getRawX(), event.getRawY());
                mEventTime = event.getDownTime();
                post(scrollRunnable);
                onCircleTouchListener.disallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mLastPoint.equals(mTempPoint)) {
                    //不相等说明滑动了，那就重新计算时间
                    mEventTime = event.getEventTime();
                }
                mLastPoint = mTempPoint;

                translate = true;
                int deltaY = tempY - mLastMotionY;
                if (onCircleTouchListener != null) {
                    Point point = onCircleTouchListener.reLayoutTop(0, deltaY);
                    int transitionY = (int) (getTranslationY() + point.y);
                    setTranslationY(transitionY);
                }
                break;
            case MotionEvent.ACTION_UP:
                removeCallbacks(scrollRunnable);
                onCircleTouchListener.disallowInterceptTouchEvent(false);
                break;
        }
        mLastMotionY = tempY;
        return true;
    }


    @Override
    public void reLayout(int left, int top, int right, int bottom) {
        left += padding;
        right = left + getMeasuredWidth();
        bottom = top + getMeasuredHeight() / 2;
        top = top - getMeasuredHeight() / 2;
        if (translate) {
            int deltaX = (int) (left - getX());
            int deltaY = (int) (top - getY());
            setTranslationX(getTranslationX() + deltaX);
            setTranslationY(getTranslationY() + deltaY);
        } else {
            layout(left, top, right, bottom);
        }
    }
}
