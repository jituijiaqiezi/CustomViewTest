package custom.region;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by linchenpeng on 2017/7/7.
 */

public class CanvasConvertTouchView2 extends BaseView {
    float downX = -1;
    float downY = -1;

    public CanvasConvertTouchView2(Context context) {
        super(context);
    }

    public CanvasConvertTouchView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasConvertTouchView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //getX获取的是相对于该View左上角的位置，getRawX是相对于屏幕左上角的位置
                downX = event.getX();
                downY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                downX = downY = -1;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float[] pts = {downX, downY};
        drawTouchCoordinateSpace(canvas);
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        drawTranslateCoordinateSpace(canvas);
        if (pts[0] == -1 && pts[1] == -1) return;

        Matrix invertMatrix = new Matrix();
        canvas.getMatrix().invert(invertMatrix);
        invertMatrix.mapPoints(pts);
        canvas.drawCircle(pts[0], pts[1], 20, mDefaultPaint);
    }

    private void drawTouchCoordinateSpace(Canvas canvas) {
        canvas.save();
        canvas.translate(10, 10);
        CanvasAidUtils.set2DAxisLength(1000, 0, 1400, 0);
        CanvasAidUtils.setLineColor(Color.GRAY);
        CanvasAidUtils.draw2DCoordinateSpace(canvas);
        canvas.restore();
    }

    private void drawTranslateCoordinateSpace(Canvas canvas) {
        CanvasAidUtils.set2DAxisLength(500, 500, 700, 700);
        CanvasAidUtils.setLineColor(Color.RED);
        CanvasAidUtils.draw2DCoordinateSpace(canvas);
        //CanvasAidUtils.draw2DCoordinateSpace(canvas);
    }
}
