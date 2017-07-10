package custom.scroller;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.lcp.customviewtest.R;

/**
 * Created by linchenpeng on 2017/7/6.
 */

public class LoginView extends RelativeLayout {
    private Scroller mScroller;
    private int mScreenHeight = 0;
    private int mScreenWidth = 0;
    private int downY = 0;
    private int moveY = 0;
    private int scrollY = 0;
    private int upY = 0;
    private boolean isMoving = false;
    private int viewHeight = 0;
    public boolean isShow = false;
    public boolean mEnabled = true;
    public boolean mOutsideTouchable = true;
    private int mDuration = 800;
    private final static String TAG = LoginView.class.getSimpleName();

    public LoginView(Context context) {
        super(context);
        init();
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoginView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setFocusable(true);
        mScroller = new Scroller(getContext());
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        setBackgroundColor(Color.argb(0, 0, 0, 0));
        final View view = View.inflate(getContext(), R.layout.view_login, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(view, params);
        setBackgroundColor(Color.argb(0, 0, 0, 0));
        view.post(new Runnable() {
            @Override
            public void run() {
                viewHeight = view.getHeight();
            }
        });
        LoginView.this.scrollTo(0, mScreenHeight);
        view.findViewById(R.id.btn_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mEnabled)
            return false;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                if (isShow)
                    return true;
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) event.getY();
                scrollY = moveY - downY;
                if (scrollY > 0) {
                    if (isShow)
                        scrollTo(0, -Math.abs(scrollY));
                } else {
                    if (mScreenHeight - getTop() <= viewHeight && !isShow)
                        scrollTo(0, Math.abs(viewHeight - scrollY));
                }
                break;
            case MotionEvent.ACTION_UP:
                upY = (int) event.getY();
                if (isShow) {
                    if (this.getScrollY() <= -viewHeight / 2) {
                        startMoveAnim(getScrollY(), -(viewHeight - getScrollY()), mDuration);
                        isShow = false;
                    } else {
                        startMoveAnim(getScrollY(), -getScrollY(), mDuration);
                        isShow = true;
                    }
                }
                changed();
                break;
            case MotionEvent.ACTION_OUTSIDE:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void startMoveAnim(int startY, int dy, int duration) {
        isMoving = true;
        mScroller.startScroll(0, startY, 0, dy, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            isMoving = true;
        } else
            isMoving = false;
        super.computeScroll();
    }

    public void show() {
        if (!isShow && !isMoving) {
            LoginView.this.startMoveAnim(-viewHeight, viewHeight, mDuration);
            isShow = true;
            changed();
        }
    }

    public void dismiss() {
        if (isShow && !isMoving) {
            LoginView.this.startMoveAnim(0, -viewHeight, mDuration);
            isShow = false;
            changed();
        }
    }

    public boolean isShow() {
        return isShow;
    }

    public boolean isSlidingEnabled() {
        return mEnabled;
    }

    public void setSlidingEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public void setOnStatusListener(onStatusListener listener) {
        this.statusListener = listener;
    }

    public void setOutsideTouchable(boolean touchable) {
        mOutsideTouchable = touchable;
    }

    public void changed() {
        if (statusListener != null) {
            if (isShow)
                statusListener.onShow();
            else
                statusListener.onDismiss();
        }
    }

    public onStatusListener statusListener;

    public interface onStatusListener {
        public void onShow();

        public void onDismiss();
    }

}
