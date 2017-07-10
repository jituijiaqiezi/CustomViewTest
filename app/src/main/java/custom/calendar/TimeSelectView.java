package custom.calendar;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.lcp.customviewtest.R;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class TimeSelectView extends RelativeLayout implements CircleView.LayoutChangeListener {
    private final static String TAG = TimeSelectView.class.getSimpleName();
    RectView rectView;
    CircleTop topCircle;
    CircleBottom bottomCircle;

    int contentPadding;

    OnCustomTouchEvent onCustomTouchEvent;
    boolean isViewAdd = false;

    public TimeSelectView(Context context) {
        this(context, null);
    }

    public TimeSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnCustomTouchEvent(OnCustomTouchEvent onCustomTouchEvent) {
        this.onCustomTouchEvent = onCustomTouchEvent;
    }

    private void init(Context context) {
        contentPadding = getResources().getDimensionPixelSize(R.dimen.time_height_half);
        rectView = new RectView(getContext());
        topCircle = new CircleTop(getContext());
        bottomCircle = new CircleBottom(getContext());

        topCircle.setLayoutChangeListener(this);
        bottomCircle.setLayoutChangeListener(this);

    }

    public void drawSelectArea(final int x, int y, final int width, final int height, final int marginLeft, final int marginTop) {
        if (isViewAdd) {
            removeView(rectView);
            removeView(topCircle);
            removeView(bottomCircle);
        } else {
            Log.i(TAG, "开始绘制啦:" + String.format("%d,%d,%d,%d", x, y, width, height));
            //如果起始点小于marginTop,则加上一半的高度;如果此时还小于，则加上一个高度
            if (y <= marginTop)
                y += height / 2;
            if (y <= marginTop)
                y += height / 2;
            final int finalY = y;
            rectView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rectView.getViewTreeObserver().removeOnPreDrawListener(this);
                    rectView.reLayout(x, finalY, width, height, marginLeft, marginTop);
                    return false;
                }
            });
            topCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    topCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                    topCircle.reLayout(x, finalY, width, height, marginLeft, marginTop);
                    return false;
                }
            });
            bottomCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    bottomCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                    bottomCircle.reLayout(x, finalY, width, height, marginLeft, marginTop);
                    return false;
                }
            });
            addView(rectView);
            addView(topCircle);
            addView(bottomCircle);
        }
        isViewAdd = !isViewAdd;
    }

    public void drawSelectArea(final View view, final int marginLeft, final int marginTop) {
        if (isViewAdd) {
            removeView(rectView);
            removeView(topCircle);
            removeView(bottomCircle);
        } else {
            rectView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rectView.getViewTreeObserver().removeOnPreDrawListener(this);
                    rectView.reLayout(view, marginLeft, marginTop);
                    return false;
                }
            });
            topCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    topCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                    topCircle.reLayout(view, marginLeft, marginTop);
                    return false;
                }
            });
            bottomCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    bottomCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                    bottomCircle.reLayout(view, marginLeft, marginTop);
                    return false;
                }
            });
            addView(rectView);
            addView(topCircle);
            addView(bottomCircle);
        }
        isViewAdd = !isViewAdd;
    }

    @Override
    public Point reLayoutTop(int deltaX, int deltaY) {
        return rectView.reLayoutTop(deltaX, deltaY);
    }

    @Override
    public Point reLayoutBottom(int deltaX, int deltaY) {
        return rectView.reLayoutBottom(deltaX, deltaY);
    }

    @Override
    public void disallowInterceptTouchEvent(boolean disallow) {
        onCustomTouchEvent.disallowInterceptTouchEvent(disallow);
    }

    @Override
    public boolean onScroll(boolean up) {
        return onCustomTouchEvent.onScroll(up);
    }

    @Override
    public boolean minHeight() {
        return rectView.getHeight() <= rectView.getMinHeight();
    }

    interface OnCustomTouchEvent {
        void disallowInterceptTouchEvent(boolean disallow);

        boolean onScroll(boolean up);
    }
}
