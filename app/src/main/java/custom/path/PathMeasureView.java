package custom.path;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lcp.customviewtest.R;

/**
 * Created by linchenpeng on 2017/7/3.
 */

public class PathMeasureView extends View {
    private float currentValue = 0;
    private float[] pos;
    private float[] tan;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private float mViewWidth, mViewHeight;
    Paint mDefaultPaint;

    public PathMeasureView(Context context) {
        this(context, null);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.arrow, options);
        mMatrix = new Matrix();
        mDefaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultPaint.setColor(Color.BLACK);
        mDefaultPaint.setStyle(Paint.Style.STROKE);
        mDefaultPaint.setStrokeWidth(5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        Path path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);
        PathMeasure measure = new PathMeasure(path, false);

        currentValue += 0.005;
        if (currentValue >= 1)
            currentValue = 0;

        /*measure.getPosTan(measure.getLength() * currentValue, pos, tan);
        mMatrix.reset();
        float degree = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        mMatrix.postRotate(degree, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);
*/
        measure.getMatrix(measure.getLength()*currentValue,mMatrix,PathMeasure.TANGENT_MATRIX_FLAG|PathMeasure.POSITION_MATRIX_FLAG);
        mMatrix.preTranslate(-mBitmap.getWidth()/2,-mBitmap.getHeight()/2);

        canvas.drawPath(path, mDefaultPaint);
        canvas.drawBitmap(mBitmap, mMatrix, mDefaultPaint);
        invalidate();
    }
}
