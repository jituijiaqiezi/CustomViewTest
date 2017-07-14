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
    private final String TAG = CircleView.class.getSimpleName();
    int DIRECTION_UP = 0, DIRECTION_DOWN = 1, DIRECTION_LEFT = 2, DIRECTION_RIGHT = 3, DIRECTION_INVALID = -1;
    boolean translate = false;
    int radius, circleStrokeWidth, padding, blockWidth, blockHeight;
    Paint innerCirclePaint, circlePaint;
    int mLastMotionX, mLastMotionY;
    int mPointerId, mTouchSlop, marginTop, marginLeft;
    OnCircleTouchListener onCircleTouchListener;
    Runnable scrollRunnable;
    long mEventTime, mLastScrollHorizontalTime;
    CustomPoint mLastPoint, mTempPoint;
    int screenWidth,screenHeight,width,height;

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
        setMeasuredDimension(width, height);
    }

    protected void init() {
        marginTop = getResources().getDimensionPixelSize(R.dimen.time_height_half);
        radius = DimensionUtil.dip2px(getContext(), 5);
        circleStrokeWidth = DimensionUtil.dip2px(getContext(), 3);

        height=getResources().getDimensionPixelSize(R.dimen.time_height);
        width=height+circleStrokeWidth;

        screenHeight=DimensionUtil.screenHeight(getContext());
        screenWidth=DimensionUtil.screenWidth(getContext());

        padding = DimensionUtil.dip2px(getContext(), 5);
        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint.setStyle(Paint.Style.FILL);
        innerCirclePaint.setColor(Color.WHITE);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(circleStrokeWidth);
        circlePaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        //setBackgroundColor(Color.CYAN);
    }

    /**
     * 滑动方向
     *
     * @return
     */
    public int scrollDirection() {
        int verticalDenominator = 9;
        int horizontalDenominator = 8;
        int[]location=locationOnScreen();

        int contentHeight = screenHeight - TimeSelectView.parentMargin;

        if (location[1] <= contentHeight / verticalDenominator+TimeSelectView.parentMargin)
            return DIRECTION_UP;
        else if (location[1] >= (verticalDenominator - 1) * contentHeight / verticalDenominator+TimeSelectView.parentMargin)
            return DIRECTION_DOWN;
        else if (location[1] >= (verticalDenominator - 6) * contentHeight / verticalDenominator+TimeSelectView.parentMargin &&
                location[1] <= (verticalDenominator - 3) * contentHeight / verticalDenominator+TimeSelectView.parentMargin) {
            if (location[0] <= 2 * screenWidth / horizontalDenominator)
                return DIRECTION_LEFT;
            else if (location[0] >= (horizontalDenominator - 1) * screenWidth / horizontalDenominator)
                return DIRECTION_RIGHT;
        }
        return DIRECTION_INVALID;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, innerCirclePaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, circlePaint);
    }

    public void reLayout(int left, int top, int right, int bottom) {
    }

    public void setCircleTouchListener(OnCircleTouchListener onCircleTouchListener) {
        this.onCircleTouchListener = onCircleTouchListener;
    }

    public void reLayout(View view, int marginLeft, int topOffset) {
        this.marginLeft = marginLeft;
        blockWidth = view.getWidth();
        blockHeight = view.getHeight();
        reLayout(marginLeft + view.getLeft(), view.getTop() + topOffset, marginLeft + view.getRight(), view.getBottom() + topOffset);
    }
    protected int locationOnScreenY(){
        int location[] = new int[2];
        getLocationOnScreen(location);
        return location[1];
    }
    protected int[] locationOnScreen(){
        int location[] = new int[2];
        getLocationOnScreen(location);
        return location;
    }
}
