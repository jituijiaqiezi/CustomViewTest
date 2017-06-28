package custom.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lcp.customviewtest.R;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class RectViewOld extends View {
    private static final String TAG=RectViewOld.class.getSimpleName();
    float strokeWidth, blockWidth, blockHeight;
    Paint innerRectPaint, rectPaint;
    int left,top,right,bottom;
    int contentPadding;

    public RectViewOld(Context context) {
        this(context, null);
    }

    public RectViewOld(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectViewOld(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        contentPadding = getResources().getDimensionPixelSize(R.dimen.time_height_half);
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
        Log.i(TAG,"onMeasure");
        int measureWidth = (int) (7 * blockWidth);
        int measureHeight = (int) (24 * blockHeight);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    public Point reLayoutTop(int deltaX, int deltaY) {
        top+=deltaY;
        //layout(left, firstTop, right, lastBottom);
        layout(left,contentPadding,(int)(7*blockWidth),(int)(24*blockHeight));
        invalidate();
        return new Point(deltaX, deltaY);
    }

    public Point reLayoutBottom(int deltaX, int deltaY) {
        if (right + deltaX <= left + blockWidth)
            deltaX = (int) (left + blockWidth - right);
        right+=deltaX;
        bottom+=deltaY;
        //layout(left, firstTop, right, lastBottom);
        layout(left,contentPadding,(int)(7*blockWidth),(int)(24*blockHeight));
        invalidate();
        return new Point(deltaX, deltaY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width=right-left;

        float count = width / blockWidth;
        if (count <= 1) {
            //开始和结束时间在同一天
            canvas.drawRect(left, top, width, right-left, rectPaint);
            //canvas.drawRect(strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth, innerRectPaint);
        } else {
            //先画开始时间
            canvas.drawRect(left, top, blockWidth, 24 * blockHeight, rectPaint);
            //再画中间的方块
            int centerBlockCount = (int) Math.max(1, count - 2);
            if (centerBlockCount >= 1)
                canvas.drawRect(blockWidth, 0, blockWidth * (centerBlockCount + 1), 24 * blockHeight, rectPaint);
            //最后画结束时间
            canvas.drawRect(blockWidth * (centerBlockCount + 1), 0, right, bottom, rectPaint);
        }

    }

    public void reLayout(View view) {
        blockWidth = view.getRight() - view.getLeft();
        blockHeight = view.getBottom() - view.getTop();
        reLayout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    public void reLayout(int left, int top, int right, int bottom) {
        this.left=left;
        this.top=top;
        this.right=right;
        this.bottom=bottom;
        //layout(left, firstTop, right, lastBottom);
        layout(left,contentPadding,(int)(7*blockWidth),(int)(24*blockHeight));
        invalidate();
    }
}
