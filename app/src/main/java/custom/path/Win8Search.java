package custom.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by linchenpeng on 2017/6/30.
 */

public class Win8Search extends View {
    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private int mWidth, mHeight;
    private ValueAnimator valueAnimator;
    private float progress;

    public Win8Search(Context context) {
        this(context, null);
    }

    public Win8Search(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Win8Search(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        valueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        mPath = new Path();
        RectF rect = new RectF(-150, -150, 150, 150);
        mPath.addArc(rect, -90, 359.9f);
        mPathMeasure = new PathMeasure(mPath, false);

        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(3000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        Path dst = new Path();
        mPathMeasure.getSegment(mPathMeasure.getLength() * progress, mPathMeasure.getLength() * progress + 1, dst, true);
        canvas.drawPath(dst, mPaint);
    }
}
