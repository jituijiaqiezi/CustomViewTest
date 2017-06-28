package custom.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    int minHeight, minWidth, maxHeight, maxWidth, marginTop, marginLeft;
    Paint innerRectPaint, rectPaint;
    int top,bottom;

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
        minHeight = DimensionUtil.dip2px(getContext(), 3);
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
        if (getTop() + deltaY <= marginTop)
            deltaY = marginTop - getTop();
        if (getTop() + deltaY + minHeight >= getBottom())
            deltaY = getBottom() - minHeight - getTop();

        reLayout(getLeft(), getTop() + deltaY, getRight(), getBottom());
        return new Point(deltaX, deltaY);
    }

    public Point reLayoutBottom(int deltaX, int deltaY) {
        if (getBottom() + deltaY - minHeight <= getTop())
            deltaY = getTop() - getBottom() + minHeight;

        if (getBottom() + deltaY >= maxHeight + marginTop)
            deltaY = maxHeight + marginTop - getBottom();
        if (getRight() + deltaX <= getLeft() + blockWidth)
            deltaX = (int) (getLeft() + blockWidth - getRight());
        int screenWidth = DimensionUtil.screenWidth(getContext());
        if (getRight() + deltaX >= screenWidth)
            deltaX = screenWidth - getRight();

        reLayout(getLeft(), getTop(), getRight() + deltaX, getBottom() + deltaY);
        return new Point(deltaX, deltaY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count = (int) Math.ceil(getWidth() / blockWidth);
        if (count <= 1) {
            //开始和结束时间在同一天
            canvas.drawRect(0, top, getWidth(), getHeight(), rectPaint);
            //canvas.drawRect(strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth, innerRectPaint);
        } else {
            //先画开始时间
            canvas.drawRect(0, top, blockWidth, getHeight(), rectPaint);
            //再画中间的方块
            if (count > 2)
                canvas.drawRect(blockWidth, 0, blockWidth * (count - 1), 24 * blockHeight, rectPaint);
            //最后画结束时间
            canvas.drawRect(blockWidth * (count - 1), 0, getWidth(), getHeight(), rectPaint);
        }

    }

    public void reLayout(View view, int marginLeft) {
        this.marginLeft = marginLeft;
        blockWidth = view.getRight() - view.getLeft();
        blockHeight = view.getBottom() - view.getTop();
        minWidth = (int) blockWidth;
        maxWidth = 7 * minWidth;
        maxHeight = (int) (24 * blockHeight);
        reLayout(marginLeft + view.getLeft(), view.getTop(), marginLeft + view.getRight(), view.getBottom());
    }

    private void reLayout(int left, int top, int right, int bottom) {
        int count = (int) Math.ceil((right - left) / blockWidth);
        Log.i(TAG,"数量:"+count);
        this.top=top;
        this.bottom=bottom;
        if (count > 1) {
            //firstTop = 0;
            //lastBottom = maxHeight+marginTop;
        }
        layout(left, top, right, bottom);
        invalidate();
    }
}
