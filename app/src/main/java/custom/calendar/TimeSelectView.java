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
    CircleTop circleTop;
    CircleBottom circleBottom;
    TimeView timeTop;
    TimeView timeBottom;
    public static int startIndex = -1;
    public static int endIndex=-1;
    public static int parentMargin;
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
        circleTop = new CircleTop(getContext());
        circleBottom = new CircleBottom(getContext());
        timeTop =new TimeView(getContext());
        timeBottom =new TimeView(getContext());

        circleTop.setCircleTouchListener(this);
        circleBottom.setCircleTouchListener(this);

    }

    public void removeView() {
        removeAllViews();
        isViewAdd = false;
    }

    public void drawSelectArea(int startIndex, final View view, final int marginLeft,int parentMargin, int scrollOffset) {
        TimeSelectView.parentMargin =parentMargin;
        if (isViewAdd) {
            removeAllViews();
            TimeSelectView.startIndex = -1;
            TimeSelectView.endIndex=-1;
        } else {
            TimeSelectView.startIndex = startIndex;
            TimeSelectView.endIndex=startIndex;
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
            circleTop.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    circleTop.getViewTreeObserver().removeOnPreDrawListener(this);
                    circleTop.reLayout(view, marginLeft,offsetTop);
                    return false;
                }
            });
            circleBottom.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    circleBottom.getViewTreeObserver().removeOnPreDrawListener(this);
                    circleBottom.reLayout(view, marginLeft,offsetTop);
                    return false;
                }
            });
            timeTop.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    timeTop.getViewTreeObserver().removeOnPreDrawListener(this);
                    timeTop.reLayout(view,offsetTop);
                    return false;
                }
            });
            timeBottom.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    timeBottom.getViewTreeObserver().removeOnPreDrawListener(this);
                    timeBottom.reLayout(view,offsetTop+view.getHeight());
                    return false;
                }
            });
            timeTop.setVisibility(View.VISIBLE);
            timeBottom.setVisibility(View.INVISIBLE);
            addView(rectView);
            addView(circleTop);
            addView(circleBottom);
            addView(timeTop);
            addView(timeBottom);
        }
        isViewAdd = !isViewAdd;
    }

    @Override
    public Point reLayoutTop(int deltaX, int deltaY) {
        Point point=rectView.reLayoutTop(deltaX, deltaY);
        timeTop.setVisibility(View.VISIBLE);
        timeBottom.setVisibility(View.INVISIBLE);
        timeTop.reLayout(point.y);
        return point;
    }

    @Override
    public Point reLayoutBottom(int deltaX, int deltaY, boolean negative) {
        Point point=rectView.reLayoutBottom(deltaX, deltaY, negative);
        timeTop.setVisibility(View.INVISIBLE);
        timeBottom.setVisibility(View.VISIBLE);
        timeBottom.reLayout(point.y);
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

    public static boolean sameIndex(){
        return startIndex==endIndex;
    }

}
