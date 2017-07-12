package custom.calendar0712;

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

public class RectView extends View {
    private static final String TAG = RectView.class.getSimpleName();
    float strokeWidth, blockWidth, blockHeight;
    //minWidth是要多于1/3 blockWidth时才添加新框
    int minWidth, minHeight, maxHeight, maxWidth, marginTop, marginLeft;
    Paint innerRectPaint, rectPaint;
    int firstTop, lastBottom;

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

        if (getRight() + deltaX <= getLeft() + blockWidth)
            deltaX = (int) (getLeft() + blockWidth - getRight());
        int screenWidth = DimensionUtil.screenWidth(getContext());
        /*if (getRight() + deltaX >= screenWidth)
            deltaX = screenWidth - getRight();*/

        reLayout(getLeft(), firstTop, getRight() + deltaX, lastBottom + deltaY);
        return new Point(deltaX, deltaY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "画onDraw:" + getTop() + "," + getY());
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

    public void reLayout(int x, int y, int width, int height, int marginLeft, int marginTop) {
        this.marginLeft = marginLeft;
        blockWidth = width;
        minWidth = (int) (blockWidth / 3);
        blockHeight = height;
        maxWidth = (int) (7 * blockWidth);
        maxHeight = (int) (24 * blockHeight);
        minHeight = (int) (2 * blockHeight / 3);
        this.marginTop = getResources().getDimensionPixelSize(R.dimen.time_height_half) + marginTop;
        reLayout(x, y,  x + width,  y + height);
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
