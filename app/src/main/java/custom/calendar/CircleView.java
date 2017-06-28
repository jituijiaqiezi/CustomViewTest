package custom.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

import com.lcp.customviewtest.R;

import util.DimensionUtil;

/**
 * Created by linchenpeng on 2017/6/27.
 */

public class CircleView extends View {
    boolean translate=false;
    int mTransitionX,mTransitionY;
    float radius, circleStrokeWidth, padding, blockWidth, blockHeight;
    Paint innerCirclePaint, circlePaint;
    int mLastMotionX, mLastMotionY;
    int mPointerId, mTouchSlop, contentPadding, marginLeft;
    LayoutChangeListener mLayoutChangeListener;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) ((radius + padding) * 2);
        setMeasuredDimension(width, width);
    }

    private void init() {
        contentPadding = getResources().getDimensionPixelSize(R.dimen.time_height_half);
        radius = DimensionUtil.dp2px(getContext(), 5);
        circleStrokeWidth = DimensionUtil.dp2px(getContext(), 3);
        padding = DimensionUtil.dp2px(getContext(), 5);
        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint.setStyle(Paint.Style.FILL);
        innerCirclePaint.setColor(Color.WHITE);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(circleStrokeWidth);
        circlePaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, innerCirclePaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, circlePaint);
    }

    protected void reLayout(int deltaX, int deltaY) {
        layout(getLeft() + deltaX, getTop() + deltaY, getRight() + deltaX, getBottom() + deltaY);
        invalidate();
    }

    public void reLayout(int left, int top, int right, int bottom) {
    }

    public void setLayoutChangeListener(LayoutChangeListener mLayoutChangeListener) {
        this.mLayoutChangeListener = mLayoutChangeListener;
    }

    interface LayoutChangeListener {
        Point reLayoutTop(int deltaX, int deltaY);

        Point reLayoutBottom(int deltaX, int deltaY);

        void disallowInterceptTouchEvent(boolean yes);
    }

    public void reLayout(View view, int marginLeft) {
        this.marginLeft = marginLeft;
        blockWidth = view.getRight() - view.getLeft();
        blockHeight = view.getBottom() - view.getTop();
        reLayout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }
}
