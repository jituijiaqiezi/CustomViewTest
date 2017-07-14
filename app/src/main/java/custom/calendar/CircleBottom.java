package custom.calendar;

import android.content.Context;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class CircleBottom extends CircleView {

    float mDownX, mDownY;
    int mLastTimes;

    private static final String TAG = CircleBottom.class.getSimpleName();

    public CircleBottom(Context context) {
        super(context);
    }

    public CircleBottom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleBottom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        scrollRunnable = new Runnable() {
            @Override
            public void run() {
                if (onCircleTouchListener != null) {
                    long nowTime = SystemClock.uptimeMillis();

                    if (!onCircleTouchListener.minHeight() && mTempPoint.equals(mLastPoint) && nowTime - mEventTime >= 1000) {
                        int direction = scrollDirection();
                        if (direction != DIRECTION_INVALID) {
                            //上下滑动
                            if (direction == DIRECTION_UP || direction == DIRECTION_DOWN) {
                                boolean canScroll = onCircleTouchListener.onScrollVertical(direction == DIRECTION_UP);
                                Log.i(TAG, "是否可以滑动:" + canScroll);
                                if (canScroll) {
                                    Point point = onCircleTouchListener.reLayoutBottom(0, direction == DIRECTION_UP ? -30 : 30, false);
                                    int transitionY = (int) (getTranslationY() + point.y);
                                    setTranslationY(transitionY);
                                }
                            } else {
                                //左右滑动
                                //和上次左右滑动的时间超过2s则可以继续滑动
                                if (nowTime - mLastScrollHorizontalTime >= 2000) {
                                    if (direction == DIRECTION_LEFT && TimeSelectView.sameIndex()) {
                                        //开始和结束时间在同一个页面，则不左滑
                                        return;
                                    }
                                    mLastScrollHorizontalTime = nowTime;
                                    boolean canScroll = onCircleTouchListener.onScrollHorizontal(direction == DIRECTION_LEFT);
                                    if (canScroll) {
                                        if (direction == DIRECTION_LEFT)
                                            TimeSelectView.endIndex--;
                                        else
                                            TimeSelectView.endIndex++;
                                    }

                                }
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
        int tempX = (int) event.getRawX();
        int tempY = (int) event.getRawY();
        mTempPoint = new CustomPoint(tempX, tempY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getRawX();
                mDownY = (int) event.getRawY();
                mLastPoint = new CustomPoint(mDownX, mDownY);
                mEventTime = event.getDownTime();
                post(scrollRunnable);
                onCircleTouchListener.disallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                // TODO: 2017/7/12 要处理滑到最右边时整个填满的问题
                if (!mLastPoint.equals(mTempPoint)) {
                    //不相等说明滑动了，那就重新计算时间
                    mEventTime = event.getEventTime();
                }
                mLastPoint = mTempPoint;
                translate = true;

                int times = ((int) Math.ceil((tempX - mDownX - blockWidth / 3) / blockWidth));
                int tempTimes = times;
                times -= mLastTimes;
                int deltaX = (int) (times * blockWidth);
                boolean negative = tempX - mDownX < 0;
                int deltaY = tempY - mLastMotionY;
                int locationY=locationOnScreenY();
                if (locationY + deltaY < TimeSelectView.parentMargin)
                    deltaY = TimeSelectView.parentMargin - locationY;
                else if (locationY + deltaY > screenHeight - getHeight())
                    deltaY = screenHeight - getHeight() - locationY;
                if (onCircleTouchListener != null && (deltaX != 0 || deltaY != 0)) {
                    Point point = onCircleTouchListener.reLayoutBottom(deltaX, deltaY, negative);
                    int transitionX = (int) (getTranslationX() + point.x);
                    int transitionY = (int) (getTranslationY() + point.y);
                    setTranslationX(transitionX);
                    setTranslationY(transitionY);
                }
                mLastTimes = tempTimes;
                break;
            case MotionEvent.ACTION_UP:
                removeCallbacks(scrollRunnable);
                onCircleTouchListener.disallowInterceptTouchEvent(false);
                mLastTimes = 0;
                break;
        }
        mLastMotionX = tempX;
        mLastMotionY = tempY;
        return true;
    }

    @Override
    public void reLayout(int left, int top, int right, int bottom) {
        left = right - width;
        top = bottom - height / 2;
        bottom = bottom + height / 2;
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
