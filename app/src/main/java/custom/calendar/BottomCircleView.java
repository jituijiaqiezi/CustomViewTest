package custom.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class BottomCircleView extends CircleView {

    private static final String TAG = BottomCircleView.class.getSimpleName();

    public BottomCircleView(Context context) {
        super(context);
    }

    public BottomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int tempX = (int) event.getRawX();
        int tempY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onDown:" + event.getX() + "**" + event.getY() + " ----- " + event.getRawX() + "**" + event.getRawY());
                mLayoutChangeListener.disallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                translate=true;
                int deltaX = tempX - mLastMotionX;
                int deltaY = tempY - mLastMotionY;
                if (mLayoutChangeListener != null) {
                    Point point = mLayoutChangeListener.reLayoutBottom(deltaX, deltaY);
                    int transitionX = (int) (getTranslationX() + point.deltaX);
                    int transitionY = (int) (getTranslationY() + point.deltaY);
                    setTranslationX(transitionX);
                    setTranslationY(transitionY);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onUp:" + event.getX() + "**" + event.getY() + " ----- " + event.getRawX() + "**" + event.getRawY());
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
            invalidate();
        }
    }
}
