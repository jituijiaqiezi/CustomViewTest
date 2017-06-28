package custom.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class BottomCircleViewOld extends CircleView {

    private static final String TAG=BottomCircleViewOld.class.getSimpleName();
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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPointerId = event.getPointerId(0);
                mLastMotionX = (int) event.getX();
                mLastMotionY = (int) event.getY();
                mLayoutChangeListener.disallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerIndex = event.findPointerIndex(mPointerId);
                float x = event.getX(pointerIndex);
                float y = event.getY(pointerIndex);
                int deltaX= (int) (x-mLastMotionX);
                int deltaY = (int) (y - mLastMotionY);

                if (mLayoutChangeListener != null ) {
                    Point point=mLayoutChangeListener.reLayoutBottom(deltaX, deltaY);
                    reLayout(point.deltaX, point.deltaY);
                    mLastMotionX = (int) x;
                    mLastMotionY = (int) y;
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastMotionX = 0;
                mLastMotionY = 0;
                mLayoutChangeListener.disallowInterceptTouchEvent(false);
                break;
        }
        Log.i(TAG,mLastMotionX+"------"+mLastMotionY);
        return true;
    }

    @Override
    public void reLayout(int left, int top, int right, int bottom) {
        left = (int) (right - getMeasuredWidth() - padding);
        top = bottom - getMeasuredHeight() / 2;
        bottom = bottom + getMeasuredHeight() / 2;
        layout(left, top, right, bottom);
        invalidate();
    }
}
