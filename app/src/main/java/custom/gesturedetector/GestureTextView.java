package custom.gesturedetector;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

/**
 * Created by linchenpeng on 2017/7/5.
 */

public class GestureTextView extends android.support.v7.widget.AppCompatTextView implements View.OnTouchListener{
    private String TAG=GestureTextView.class.getSimpleName();
    private GestureDetector gestureDetector;
    private VelocityTracker velocityTracker;
    public GestureTextView(Context context) {
        this(context,null);
    }

    public GestureTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GestureTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        velocityTracker=VelocityTracker.obtain();
        setOnTouchListener(this);
        setClickable(true);
        setLongClickable(true);
        gestureDetector=new GestureDetector(context,new CustomGesutreListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        velocityTracker.addMovement(event);
        velocityTracker.computeCurrentVelocity(1000);
        showToast("速率检测:"+velocityTracker.getXVelocity()+"***"+velocityTracker.getYVelocity());
        return gestureDetector.onTouchEvent(event);
    }

    class CustomGesutreListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            showToast("双击");
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            showToast("双击事件");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            showToast("单击确认");
            return true;
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            showToast("上下文点击");
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            showToast("点下");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            showToast("显示点击");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            showToast("单击放开");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            showToast("滑动");
            scrollBy((int)distanceX,(int)distanceY);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            showToast("长按");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            showToast("迅速滑动");
            return true;
        }
    }
    public void showToast(String message){
        //Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
        Log.i(TAG,message);
    }
}
