package custom.floatingbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lcp.datepickertest.R;

import util.DimentionUtil;

/**
 * Created by linchenpeng on 2017/6/15.
 */

public class FloatingButton extends View {
    private static final String TAG=FloatingButton.class.getSimpleName();
    Paint backgroundPaint;
    Paint imagePaint;
    float circleRaidus;
    float imageRadius;

    public FloatingButton(Context context) {
        this(context, null);
    }

    public FloatingButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(getResources().getColor(R.color.red, null));

        imagePaint = new Paint();
        imagePaint.setAntiAlias(true);
        imagePaint.setStyle(Paint.Style.FILL);
        imagePaint.setColor(Color.WHITE);

        TypedArray array=getContext().obtainStyledAttributes(attrs,R.styleable.FloatingButton);
        circleRaidus=array.getDimensionPixelSize(R.styleable.FloatingButton_circleRadius, (int) DimentionUtil.dp2px(getContext(),40));
        imageRadius=array.getDimensionPixelSize(R.styleable.FloatingButton_imageRadius,2);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
       /* canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));*/
        float centerX = (getWidth() + getPaddingLeft() - getPaddingRight()) / 2;
        float centerY = (getHeight() + getPaddingTop() - getPaddingBottom()) / 2;
        Log.i(TAG,"位置:"+getX()+"*"+getY()+",大小:"+getWidth()+"*"+getHeight());
        int size=(getWidth()-getPaddingLeft()-getPaddingRight())/2;
        canvas.drawCircle(centerX, centerY, size, backgroundPaint);
        float roundRadius = 2;
        float margin=20;

        canvas.drawRoundRect(centerX-size+margin/2, centerY - margin/2,
                centerX+size-margin/2, centerY+margin/2, roundRadius, roundRadius, imagePaint);
        canvas.drawRoundRect(centerX - margin/2, centerY-size+margin/2,
                centerX + margin/2, centerY+size-margin/2, roundRadius, roundRadius, imagePaint);
    }
}
