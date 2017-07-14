package custom.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lcp.customviewtest.R;

import util.DimensionUtil;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class RectView extends View {
    private static final String TAG = RectView.class.getSimpleName();
    float strokeWidth, blockWidth, blockHeight;
    int minHeight, maxHeight, maxWidth, marginTop, marginLeft;
    Paint innerRectPaint, rectPaint;
    int firstTop, lastBottom;
    int screenWidth, screenHeight;

    public RectView(Context context) {
        this(context, null);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        screenWidth = DimensionUtil.screenWidth(getContext());
        screenHeight = DimensionUtil.screenHeight(getContext());
        marginTop = getResources().getDimensionPixelSize(R.dimen.time_height_half);
        blockHeight = getResources().getDimensionPixelSize(R.dimen.content_height);
        strokeWidth = 2;
        innerRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerRectPaint.setColor(getResources().getColor(R.color.main_blue_light));
        innerRectPaint.setStyle(Paint.Style.FILL);
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(getResources().getColor(R.color.main_blue));
        rectPaint.setStyle(Paint.Style.FILL);
        //rectPaint.setStyle(Paint.Style.STROKE);
        //rectPaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = (int) (7 * blockWidth);
        int measureHeight = (int) (24 * blockHeight + 2 * marginTop);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    public Point reLayoutTop(int deltaX, int deltaY) {

        if (firstTop + deltaY <= marginTop)
            deltaY = marginTop - firstTop;
        if (firstTop + deltaY >= maxHeight + marginTop)
            deltaY = maxHeight + marginTop - firstTop;
        //只有当开始和结束时间在同一天时才需要处理这种情况
        int count = (int) Math.ceil(getWidth() / blockWidth);
        if (count <= 1 && firstTop + deltaY + minHeight >= lastBottom)
            deltaY = lastBottom - minHeight - firstTop;

        reLayout(getLeft(), firstTop + deltaY, getRight(), lastBottom);
        return new Point(deltaX, deltaY);
    }

    public Point reLayoutBottom(int deltaX, int deltaY, boolean negative) {

        //只有当开始和结束时间在同一天时才需要处理这种情况
        int count = (int) Math.ceil(getWidth() / blockWidth);
        if (count <= 1 && negative)
            deltaX = 0;
        if (count <= 1 && lastBottom + deltaY - minHeight <= firstTop)
            deltaY = firstTop - lastBottom + minHeight;
        if (count >= 2 && lastBottom + deltaY <= marginTop) {
            deltaY = marginTop - lastBottom;
        }

        if (lastBottom + deltaY >= maxHeight + marginTop)
            deltaY = maxHeight + marginTop - lastBottom;

        if (getRight() + deltaX <= getLeft() + blockWidth)
            deltaX = (int) (getLeft() + blockWidth - getRight());
        else if (getRight() + deltaX >= screenWidth)
            deltaX = screenWidth - getRight();

        reLayout(getLeft(), firstTop, getRight() + deltaX, lastBottom + deltaY);
        return new Point(deltaX, deltaY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count = (int) Math.ceil(getWidth() / blockWidth);
        if (count <= 1) {
            //开始和结束时间在同一天
            canvas.drawRect(0, 0, getWidth(), getHeight(), rectPaint);
            //canvas.drawRect(strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth, innerRectPaint);
        } else {
            //先画开始时间
            canvas.drawRect(0, firstTop - marginTop, blockWidth, getHeight(), rectPaint);
            //再画中间的方块
            if (count > 2)
                canvas.drawRect(blockWidth, 0, blockWidth * (count - 1), 24 * blockHeight, rectPaint);
            //最后画结束时间
            canvas.drawRect(blockWidth * (count - 1), 0, getWidth(), lastBottom - marginTop, rectPaint);
        }

    }

    public void reLayout(View view, int marginLeft, int topOffset) {
        this.marginLeft = marginLeft;
        blockWidth = view.getWidth();
        blockHeight = view.getHeight();
        maxWidth = (int) (7 * blockWidth);
        maxHeight = (int) (24 * blockHeight);
        //minHeight = (int) (1 * blockHeight / 4);
        reLayout(marginLeft + view.getLeft(), view.getTop() + topOffset, marginLeft + view.getRight(), view.getBottom() + topOffset);
    }

    private void reLayout(int left, int top, int right, int bottom) {
        int count = (int) Math.ceil((right - left) / blockWidth);
        this.firstTop = top;
        this.lastBottom = bottom;
        if (count > 1) {
            top = marginTop;
            bottom = maxHeight + marginTop;
        }
        layout(left, top, right, bottom);
        invalidate();
    }

    public int getMinHeight() {
        return minHeight;
    }
}
