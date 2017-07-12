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

public class TimeSelectView extends RelativeLayout implements OnCircleTouchListener {
    private final static String TAG = TimeSelectView.class.getSimpleName();
    RectView rectView;
    CircleTop topCircle;
    CircleBottom bottomCircle;
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

        topCircle.setCircleTouchListener(this);
        bottomCircle.setCircleTouchListener(this);

    }

    public void removeView() {
        removeAllViews();
        isViewAdd = false;
    }

    public void drawSelectArea(int startIndex, final View view, final int marginLeft){
        Log.i(TAG,String.format("left:%d,top:%d,right:%d,bottom:%d,marginLeft:%d",view.getLeft(),view.getTop(),view.getRight(),view.getBottom(),marginLeft));
        if (isViewAdd) {
            removeAllViews();
            this.startIndex = -1;
        } else {
            this.startIndex = startIndex;
            rectView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rectView.getViewTreeObserver().removeOnPreDrawListener(this);
                    rectView.reLayout(view, marginLeft);
                    return false;
                }
            });
            topCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    topCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                    topCircle.reLayout(view, marginLeft);
                    return false;
                }
            });
            bottomCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    bottomCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                    bottomCircle.reLayout(view, marginLeft);
                    return false;
                }
            });
            /*RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            rectView.setLayoutParams(params);*/
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