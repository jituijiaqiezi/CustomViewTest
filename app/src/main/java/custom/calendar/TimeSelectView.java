package custom.calendar;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.lcp.customviewtest.R;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class TimeSelectView extends RelativeLayout implements OnCircleTouchListener {
    private final static String TAG = TimeSelectView.class.getSimpleName();
    RectView rectView;
    CircleTop topCircle;
    CircleBottom bottomCircle;
    TimeView topTimeView;
    TimeView bottomTimeView;
    int startIndex = -1;
    int contentPadding;

    OnCustomTouchListener onTouchListener;
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

    public void setOnCustomTouchListener(OnCustomTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    private void init(Context context) {
        contentPadding = getResources().getDimensionPixelSize(R.dimen.time_height_half);
        rectView = new RectView(getContext());
        topCircle = new CircleTop(getContext());
        bottomCircle = new CircleBottom(getContext());
        topTimeView =new TimeView(getContext());
        bottomTimeView=new TimeView(getContext());

        topCircle.setCircleTouchListener(this);
        bottomCircle.setCircleTouchListener(this);

    }

    public void removeView() {
        removeAllViews();
        isViewAdd = false;
    }

    public void drawSelectArea(int startIndex, final View view, final int marginLeft, int scrollOffset) {
        if (isViewAdd) {
            removeAllViews();
            this.startIndex = -1;
        } else {
            this.startIndex = startIndex;
            int topOffset = 0;
            int height = view.getHeight();
            if (view.getTop() + height / 3 < scrollOffset) {
                topOffset += height;
            } else if (view.getTop() < scrollOffset) {
                topOffset += height / 2;
            }
            final int offsetTop=topOffset;

            rectView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rectView.getViewTreeObserver().removeOnPreDrawListener(this);
                    rectView.reLayout(view, marginLeft,offsetTop);
                    return false;
                }
            });
            topCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    topCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                    topCircle.reLayout(view, marginLeft,offsetTop);
                    return false;
                }
            });
            bottomCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    bottomCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                    bottomCircle.reLayout(view, marginLeft,offsetTop);
                    return false;
                }
            });
            topTimeView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    topTimeView.getViewTreeObserver().removeOnPreDrawListener(this);
                    topTimeView.reLayout(view,offsetTop);
                    return false;
                }
            });
            bottomTimeView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    bottomTimeView.getViewTreeObserver().removeOnPreDrawListener(this);
                    bottomTimeView.reLayout(view,offsetTop+view.getHeight());
                    return false;
                }
            });
            topTimeView.setVisibility(View.VISIBLE);
            bottomTimeView.setVisibility(View.INVISIBLE);
            addView(rectView);
            addView(topCircle);
            addView(bottomCircle);
            addView(topTimeView);
            addView(bottomTimeView);
        }
        isViewAdd = !isViewAdd;
    }

    @Override
    public Point reLayoutTop(int deltaX, int deltaY) {
        Point point=rectView.reLayoutTop(deltaX, deltaY);
        topTimeView.setVisibility(View.VISIBLE);
        bottomTimeView.setVisibility(View.INVISIBLE);
        topTimeView.reLayout(point.y);
        return point;
    }

    @Override
    public Point reLayoutBottom(int deltaX, int deltaY, boolean negative) {
        Point point=rectView.reLayoutBottom(deltaX, deltaY, negative);
        topTimeView.setVisibility(View.INVISIBLE);
        bottomTimeView.setVisibility(View.VISIBLE);
        bottomTimeView.reLayout(point.y);
        return point;
    }

    @Override
    public void disallowInterceptTouchEvent(boolean disallow) {
        onTouchListener.disallowInterceptTouchEvent(disallow);
    }

    @Override
    public boolean onScrollVertical(boolean up) {
        return onTouchListener.onScrollVertical(up);
    }

    @Override
    public boolean onScrollHorizontal(boolean left) {
        return onTouchListener.onScrollHorizontal(left);
    }

    @Override
    public boolean minHeight() {
        return rectView.getHeight() <= rectView.getMinHeight();
    }


}
