package custom.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lcp.customviewtest.R;

import util.DimensionUtil;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class RectViewOld2 extends View {
    private static final String TAG = RectViewOld2.class.getSimpleName();
    float strokeWidth, blockWidth, blockHeight;
    //minWidth是要多于1/3 blockWidth时才添加新框
    int minWidth, minHeight, maxHeight, maxWidth, marginTop, marginLeft;
    Paint innerRectPaint, rectPaint;
    int firstTop, lastBottom;

    public RectViewOld2(Context context) {
        this(context, null);
    }

    public RectViewOld2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectViewOld2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        marginTop = getResources().getDimensionPixelSize(R.dimen.time_height_half);
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
        Log.i(TAG, "onMeasure");
        int measureWidth = (int) (7 * blockWidth);
        int measureHeight = (int) (24 * blockHeight);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    public Point reLayoutTop(int deltaX, int deltaY) {
        if (firstTop + deltaY <= marginTop)
            deltaY = marginTop - firstTop;
        //只有当开始和结束时间在同一天时才需要处理这种情况
        int count = (int) Math.ceil(getWidth() / blockWidth);
        if (count <= 1 && firstTop + deltaY + minHeight >= lastBottom)
            deltaY = lastBottom - minHeight - firstTop;

        reLayout(getLeft(), firstTop + deltaY, getRight(), lastBottom);
        return new Point(deltaX, deltaY);
    }

    public Point reLayoutBottom(int deltaX, int deltaY) {
        //只有当开始和结束时间在同一天时才需要处理这种情况
        int count = (int) Math.ceil(getWidth() / blockWidth);
        if (count <= 1 && lastBottom + deltaY - minHeight <= firstTop)
            deltaY = firstTop - lastBottom + minHeight;
        else if (count >= 2 && lastBottom + deltaY <= marginTop) {
            deltaY = marginTop - lastBottom;
        }
        if (lastBottom + deltaY >= maxHeight + marginTop)
            deltaY = maxHeight + marginTop - lastBottom;

        // TODO: 2017/7/4 处理只有当超过1/3时才显示第二个框
        if (getRight() + deltaX <= getLeft() + blockWidth)
            deltaX = (int) (getLeft() + blockWidth - getRight());
        int screenWidth = DimensionUtil.screenWidth(getContext());
        if (getRight() + deltaX >= screenWidth)
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

    public void reLayout(View view, int marginLeft) {
        this.marginLeft = marginLeft;
        blockWidth = view.getRight() - view.getLeft();
        minWidth = (int) (blockWidth / 3);
        blockHeight = view.getBottom() - view.getTop();
        maxWidth = (int) (7 * blockWidth);
        maxHeight = (int) (24 * blockHeight);
        minHeight = (int) (2 * blockHeight / 3);
        reLayout(marginLeft + view.getLeft(), view.getTop(), marginLeft + view.getRight(), view.getBottom());
    }

    private void reLayout(int left, int top, int right, int bottom) {
        int count = (int) Math.ceil((right - left) / blockWidth);
        Log.i(TAG, "lastBottom:" + lastBottom);
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
