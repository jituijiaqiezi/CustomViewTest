package custom.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

public class CalendarScrollView extends ScrollView {
    private final String TAG=CalendarScrollView.class.getSimpleName();
    private int downX;
    private int downY;
    private int mTouchSlop;

    public CalendarScrollView(Context context) {
        this(context, null, 0);
    }

    public CalendarScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }

    public int scrollX(){
        return computeVerticalScrollOffset();
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        /*if (t + getHeight() >= computeVerticalScrollRange()) {
            Log.i(TAG, "到底了");
        } else {
            Log.i(TAG, "没有到底");
        }*/
    }
}