package custom.spinnerloading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by linchenpeng on 2017/6/15.
 */

public class SpinnerLoadingView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG=SpinnerLoadingView.class.getSimpleName();
    Runnable runnable;
    boolean rotate;
    long frame;
    int angle=0;
    Paint paint;
    public SpinnerLoadingView(Context context){
        this(context,null);
    }
    public SpinnerLoadingView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public SpinnerLoadingView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        init(context);
    }
    private void init(Context context){
        paint=new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        frame=1000/12;
        runnable=new Runnable() {
            @Override
            public void run() {
                angle+=30;
                if(angle>360)
                    angle-=360;
                postInvalidate();
                if(rotate)
                    postDelayed(runnable,frame);
            }
        };
        post(runnable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG,getWidth()+"*"+getHeight());
        canvas.drawRect(0,0,getRight(),getBottom(),paint);
        canvas.rotate(angle,getWidth()/2,getHeight()/2);
        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        rotate=true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        rotate=false;
    }
}
