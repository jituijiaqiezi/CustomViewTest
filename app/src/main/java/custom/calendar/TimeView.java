package custom.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lcp.customviewtest.R;

import util.DimensionUtil;

/**
 * Created by linchenpeng on 2017/7/13.
 */

public class TimeView extends View {
    private final String TAG = TimeView.class.getSimpleName();
    int height, triangleLength, circleRadius;
    Paint paint,textPaint;
    Path path;

    public TimeView(Context context) {
        super(context);
        init();
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        height = DimensionUtil.dip2px(getContext(), 18);
        triangleLength = DimensionUtil.dip2px(getContext(), 8);
        circleRadius = DimensionUtil.dip2px(getContext(), 2);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.main_blue));
        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(12);
        path = new Path();
    }

    public void reLayout(View view, int offsetTop) {
        int top = view.getTop() + offsetTop - height / 2;
        int right = view.getWidth();
        int rectRight=right-triangleLength;
        int bottom = top + height;
        path.reset();
        path.addRoundRect(new RectF(0, 0, rectRight, height), circleRadius, circleRadius, Path.Direction.CW);
        path.moveTo(rectRight,height/2-triangleLength/2);
        path.lineTo(right,height/2);
        path.lineTo(rectRight,height/2+triangleLength/2);
        layout(0, top, right, bottom);
        invalidate();
    }
    public void reLayout(int deltaY){
        layout(getLeft(),getTop()+deltaY,getRight(),getBottom()+deltaY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
        Paint.FontMetrics metrics=textPaint.getFontMetrics();
        String text="18:30";
        float width=textPaint.measureText(text);
        canvas.drawText(text,(getWidth()-triangleLength-width)/2,(getHeight()+metrics.descent+metrics.ascent)/2,textPaint);

    }
}
