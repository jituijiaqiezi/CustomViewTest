package custom.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by linchenpeng on 2017/6/30.
 */

public class PathView extends View {
    private Paint mPaint;
    private Path mPath;
    private int mWidth,mHeight;


    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setAntiAlias(true);

        mPath = new Path();
        RectF rect = new RectF(-150, -150, 150, 150);
        mPath.addArc(rect, -90, 359.9f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path=new Path();
        /*path.moveTo(20,200);
        path.lineTo(100,100);
        path.lineTo(100,200);
        path.lineTo(150,250);
        path.close();
        canvas.drawPath(path,mPaint);*/
        Rect rect=new Rect(100,100,300,250);
        /*canvas.drawRect(rect,mPaint);

        //canvas.drawOval(new RectF(rect),mPaint);
        canvas.drawLine(50,175,350,175,mPaint);
        canvas.drawLine(200,50,200,300,mPaint);*/
        //path.addArc(new RectF(rect),30,60);
        path.moveTo(0,0);
        path.arcTo(new RectF(rect),30,60);
        path.close();
        path.reset();
        //二次贝塞尔曲线
        path.moveTo(20,50);
        path.lineTo(50,200);
        path.quadTo(100,200,150,250);
        path.reset();
        //绘制圆
        path.addCircle(100,100,50, Path.Direction.CCW);
        path.addPath(path,400,0);
        path.reset();

        canvas.save();
        canvas.translate(mWidth/2,mHeight/2);
        path.lineTo(200,200);
        path.lineTo(200,0);
        canvas.drawPath(path,mPaint);
        canvas.restore();
        path.lineTo(300,300);
        canvas.drawPath(path,mPaint);
    }
}
