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

public class BottomCircleViewOld extends CircleView {

    private static final String TAG = BottomCircleViewOld.class.getSimpleName();

    public BottomCircleViewOld(Context context) {
        super(context);
    }

    public BottomCircleViewOld(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomCircleViewOld(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        scrollRunnable = new Runnable() {
            @Override
            public void run() {
                if (mLayoutChangeListener != null) {
                    // TODO: 2017/6/29  首先应该判断方框的大小是否小等于最小高度，如果是，则不向上滑动了
                    if (!mLayoutChangeListener.minHeight()&&mTempPoint.equals(mLastPoint) && SystemClock.uptimeMillis() - mEventTime >= 500) {
                        boolean up = upScroll();
                        boolean canScroll=mLayoutChangeListener.onScroll(up);
                        if (canScroll&&mLayoutChangeListener != null) {
                            Point point = mLayoutChangeListener.reLayoutBottom(0, up ? -30 : 30);
                            int transitionY = (int) (getTranslationY() + point.y);
                            setTranslationY(transitionY);
                        }
                    }
                }
                postDelayed(scrollRunnable, 30);
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int tempX = (int) event.getRawX();
        int tempY = (int) event.getRawY();
        mTempPoint = new CustomPoint(event.getRawX(), event.getRawY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastPoint = new CustomPoint(event.getRawX(), event.getRawY());
                mEventTime = event.getDownTime();
                post(scrollRunnable);
                mLayoutChangeListener.disallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mLastPoint.equals(mTempPoint)) {
                    //不相等说明滑动了，那就重新计算时间
                    mEventTime = event.getEventTime();
                }
                mLastPoint = mTempPoint;

                translate=true;
                int deltaX = tempX - mLastMotionX;
                int deltaY = tempY - mLastMotionY;
                if (mLayoutChangeListener != null) {
                    Point point = mLayoutChangeListener.reLayoutBottom(deltaX, deltaY);
                    int transitionX = (int) (getTranslationX() + point.x);
                    int transitionY = (int) (getTranslationY() + point.y);
                    setTranslationX(transitionX);
                    setTranslationY(transitionY);
                }
                break;
            case MotionEvent.ACTION_UP:
                removeCallbacks(scrollRunnable);
                mLayoutChangeListener.disallowInterceptTouchEvent(false);
                break;
        }
        mLastMotionX = tempX;
        mLastMotionY = tempY;
        return true;
    }

    @Override
    public void reLayout(int left, int top, int right, int bottom) {
        left = (int) (right - getMeasuredWidth() - padding);
        top = bottom - getMeasuredHeight() / 2;
        bottom = bottom + getMeasuredHeight() / 2;
        if (translate) {
            int deltaX = (int) (marginLeft+left - getX());
            int deltaY = (int) (top - getY());
            setTranslationX(getTranslationX() + deltaX);
            setTranslationY(getTranslationY() + deltaY);
        } else {
            layout(marginLeft + left, top, marginLeft + right, bottom);
            //invalidate();
        }
    }
}
