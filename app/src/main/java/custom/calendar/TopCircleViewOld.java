package custom.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class TopCircleViewOld extends CircleView {
    private static final String TAG = TopCircleViewOld.class.getSimpleName();


    public TopCircleViewOld(Context context) {
        super(context);
    }

    public TopCircleViewOld(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TopCircleViewOld(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPointerId = event.getPointerId(0);
                mLastMotionY = (int) event.getY();
                mLayoutChangeListener.disallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerIndex = event.findPointerIndex(mPointerId);
                float y = event.getY(pointerIndex);
                float deltaY = y - mLastMotionY;
                if (getTop() + deltaY <= contentPadding - getMeasuredHeight() / 2)
                    deltaY = contentPadding - getMeasuredHeight() / 2 - getTop();
                if (mLayoutChangeListener != null ) {
                    Point point=mLayoutChangeListener.reLayoutTop(0, (int) deltaY);
                    reLayout(point.deltaX, point.deltaY);
                    mLastMotionY = (int) y;
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastMotionY = 0;
                mLayoutChangeListener.disallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }


    @Override
    public void reLayout(int left, int top, int right, int bottom) {
        left += padding;
        right = left + getMeasuredWidth();
        bottom = top + getMeasuredHeight() / 2;
        top = top - getMeasuredHeight() / 2;
        layout(left, top, right, bottom);
        invalidate();
    }
}
