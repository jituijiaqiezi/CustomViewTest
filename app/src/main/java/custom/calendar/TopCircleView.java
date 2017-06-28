package custom.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class TopCircleView extends CircleView {
    private static final String TAG = TopCircleView.class.getSimpleName();

    public TopCircleView(Context context) {
        super(context);
    }

    public TopCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TopCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int tempY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLayoutChangeListener.disallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                translate = true;
                int deltaY = tempY - mLastMotionY;
                /*if (getTop() + deltaY <= contentPadding - getMeasuredHeight() / 2)
                    deltaY = contentPadding - getMeasuredHeight() / 2 - getTop();*/
                if (mLayoutChangeListener != null) {
                    Point point = mLayoutChangeListener.reLayoutTop(0, deltaY);
                    //reLayout(point.deltaX, point.deltaY);
                    int transitionY = (int) (getTranslationY() + point.deltaY);
                    setTranslationY(transitionY);
                }
                break;
            case MotionEvent.ACTION_UP:
                mLayoutChangeListener.disallowInterceptTouchEvent(false);
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
        /*layout(marginLeft+left, firstTop, marginLeft+right, lastBottom);
        invalidate();*/
        if (translate) {
            int deltaX = (int) (marginLeft+left - getX());
            int deltaY = (int) (top - getY());
            setTranslationX(getTranslationX() + deltaX);
            setTranslationY(getTranslationY() + deltaY);
        } else {
            layout(marginLeft + left, top, marginLeft + right, bottom);
            forceLayout();
        }
    }
}
