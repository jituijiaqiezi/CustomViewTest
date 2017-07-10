package custom.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import com.lcp.customviewtest.R;

/**
 * Created by linchenpeng on 2017/7/7.
 */

public class PullToRefreshScrollView extends ViewGroup {

    private FooterLayout footerLayout;
    private View topLayout;
    private int desireWidth, desireHeight;
    private VelocityTracker velocityTracker;
    private int mPointerId;
    private float x, y;
    private OverScroller mScroller;
    private int maxFlingVelocity, minFlingVelocity;
    private int mTouchSlop;
    protected Boolean isMove = false;
    protected float downX = 0, downY = 0;
    private int topHeight = 0;
    private int scrollYBottom = 0;
    private int nScrollBottom = 0;
    private int pullDownMin = 0;
    private boolean isEnablePullDown = true;
    private boolean isFirst = true;

    public void setEnablePullDown(boolean isEnablePullDown) {
        this.isEnablePullDown = isEnablePullDown;
    }

    public PullToRefreshScrollView(Context context) {
        super(context);
        init();
    }

    public PullToRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new OverScroller(getContext());
        maxFlingVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        minFlingVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        footerLayout = (FooterLayout) findViewById(R.id.footer_layout);
        topLayout = findViewById(R.id.top_layout);
        if (isEnablePullDown) {
            footerLayout.showFooterPull();
        } else {
            footerLayout.hideFooter();
        }
    }

    public int getScrollYTop() {
        return topHeight;
    }

    public int getScrollYBottom() {
        return scrollYBottom;
    }

    public int getNScrollYTop() {
        return 0;
    }

    public int getNScrollYBottom() {
        return nScrollBottom;
    }

    public int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int measureMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        switch (measureMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = width;
                break;
            default:
                break;
        }
        return result;
    }

    public int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int measureMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        switch (measureMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = height;
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        desireWidth = 0;
        desireHeight = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View v = getChildAt(i);
            if (v.getVisibility() != GONE) {
                LayoutParams params = (LayoutParams) v.getLayoutParams();
                measureChildWithMargins(v, widthMeasureSpec, 0, heightMeasureSpec, 0);
                if (v.getId() == R.id.top_layout)
                    topHeight = v.getMeasuredHeight();
                desireWidth = Math.max(desireWidth, v.getMeasuredWidth() + params.leftMargin + params.rightMargin);
                desireHeight += v.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            }
        }

        desireWidth += getPaddingLeft() + getPaddingRight();
        desireHeight += getPaddingTop() + getPaddingBottom();
        desireWidth = Math.max(desireWidth, getSuggestedMinimumWidth());
        desireHeight = Math.max(desireHeight, getSuggestedMinimumHeight());

        int scrollHeight = height + topHeight * 2;
        if (scrollHeight > desireWidth) {
            int offset = scrollHeight - desireHeight;
            View view = new View(getContext());
            view.setBackgroundResource(R.color.top_layout_color);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, offset);
            addView(view, getChildCount() - 1, params);
            desireWidth = scrollHeight;
        }
        setMeasuredDimension(resolveSize(desireWidth, widthMeasureSpec), resolveSize(desireHeight, heightMeasureSpec));

        scrollYBottom = desireHeight - getMeasuredHeight() - topHeight;
        nScrollBottom = desireHeight - getMeasuredHeight();
        pullDownMin = nScrollBottom - topHeight / 2;
        if (isFirst) {
            scrollTo(0, topHeight);
            isFirst = false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentLeft = getPaddingLeft();
        final int parentRight = r - l - getPaddingRight();
        final int parentTop = getPaddingTop();
        final int parentBottom = b - t - getPaddingBottom();
        int left = parentLeft;
        int top = getTop();

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != GONE) {
                LayoutParams params = (LayoutParams) childView.getLayoutParams();
                final int childWidth = childView.getMeasuredWidth();
                final int childHeight = childView.getMeasuredHeight();
                final int gravity = params.gravity;
                final int horizontalGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
                final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

                left = parentLeft;
                top += params.topMargin;
                switch (horizontalGravity) {
                    case Gravity.LEFT:
                        break;
                    case Gravity.CENTER_HORIZONTAL:
                        left = parentLeft + (parentRight - parentLeft - childWidth) / 2 + params.leftMargin - params.rightMargin;
                        break;
                    case Gravity.RIGHT:
                        left = parentRight - childWidth - params.rightMargin;
                }
                childView.layout(left, top, left + childWidth, top + childHeight);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN && ev.getEdgeFlags() != 0)
            return false;
        boolean isIntercept = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isIntercept = !mScroller.isFinished();
                mPointerId = ev.getPointerId(0);
                downX = x = ev.getX();
                downY = y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerIndex = ev.findPointerIndex(mPointerId);
                float mx = ev.getX(pointerIndex);
                float my = ev.getY(pointerIndex);
                if (Math.abs(y - my) >= mTouchSlop)
                    isIntercept = true;
                if (isIntercept) {
                    x = mx;
                    y = my;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                solvePointerUp(ev);
                break;
        }
        return isIntercept;
    }

    private void solvePointerUp(MotionEvent event) {
        //非最后一个离开的手指抬起动作
        int pointerIndexLeave = event.getActionIndex();
        int pointerIdLeave = event.getPointerId(pointerIndexLeave);
        if (mPointerId == pointerIdLeave) {
            int reIndex = pointerIndexLeave == 0 ? 1 : 0;
            mPointerId = event.getPointerId(reIndex);
            x = event.getX(reIndex);
            y = event.getY(reIndex);
            if (velocityTracker != null)
                velocityTracker.clear();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isMove = false;
                mPointerId = event.getPointerId(0);
                x = event.getX();
                y = event.getY();
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                final int pointerIndex = event.findPointerIndex(mPointerId);
                float mx = event.getX(pointerIndex);
                float my = event.getY(pointerIndex);
                moveBy((int) (x - mx), (int) (y - my));
                x = mx;
                y = my;
                break;
            case MotionEvent.ACTION_UP:
                isMove = false;
                velocityTracker.computeCurrentVelocity(1000, maxFlingVelocity);
                float velocityX = velocityTracker.getXVelocity(mPointerId);
                float velocityY = velocityTracker.getYVelocity(mPointerId);
                completeMove(-velocityX, -velocityY);
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isMove = false;
                int pointerIndexLeave = event.getActionIndex();
                int pointerIdLeave = event.getPointerId(pointerIndexLeave);
                if (mPointerId == pointerIdLeave) {
                    int reIndex = pointerIndexLeave == 0 ? 1 : 0;
                    mPointerId = event.getPointerId(reIndex);
                    x = event.getX(reIndex);
                    y = event.getY(reIndex);
                    if (velocityTracker != null)
                        velocityTracker.clear();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                isMove = false;
                break;
        }
        return true;
    }

    private boolean isPull = false;

    public void moveBy(int deltaX, int deltaY) {
        if (Math.abs(deltaY) >= Math.abs(deltaX)) {
            int mScrollY = getScrollY();
            if (mScrollY <= 0)
                scrollTo(0, 0);
            else if (mScrollY >= getNScrollYBottom())
                scrollTo(0, getNScrollYBottom());
            else
                scrollBy(0, deltaY);

            if (isEnablePullDown && deltaY > 0 && mScrollY >= pullDownMin)
                isPull = true;
        }
    }

    private void completeMove(float velocityX, float velocityY) {
        int mScrollY = getScrollY();
        int maxY = getScrollYBottom();
        int minY = getScrollYTop();
        if (mScrollY >= maxY) {
            if (isPull) {
                mScroller.startScroll(0, mScrollY, getNScrollYBottom() - mScrollY, 300);
                invalidate();

                footerLayout.showFooterRadar();
                if (pullDownListener != null)
                    pullDownListener.onPullDown();
            } else {
                mScroller.startScroll(0, mScrollY, 0, maxY - mScrollY);
                invalidate();
            }
        } else if (mScrollY <= minY) {
            mScroller.startScroll(0, mScrollY, 0, minY - mScrollY);
            invalidate();
        } else if (Math.abs(velocityY) >= minFlingVelocity && maxY > 0) {
            mScroller.fling(0, mScrollY, 0, (int) (velocityY * 2f), 0, 0, getNScrollYTop(), getNScrollYBottom());
            invalidate();
        }
    }

    public void ifNeedScrollBack() {
        int mScrollY = getScrollY();
        int maxY = getScrollYBottom();
        int minY = getScrollYTop();

        if (mScrollY > maxY) {
            mScroller.startScroll(0, mScrollY, 0, maxY - mScrollY);
            invalidate();
        } else if (mScrollY < minY) {
            mScroller.startScroll(0, mScrollY, 0, minY - mScrollY);
            invalidate();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        } else {
            if (!isMove && !isPull)
                ifNeedScrollBack();
        }
    }

    public void onPullSuccess() {
        scrollToBack();
    }

    public void scrollToBack() {
        int mScrollY = getScrollY();
        int maxY = getScrollYBottom();
        mScroller.startScroll(0, mScrollY, 0, maxY - mScrollY, 300);
        invalidate();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                footerLayout.showFooterPull();
                isPull = false;
            }
        }, 310);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends MarginLayoutParams {
        public int gravity = -1;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlideGroup);
            gravity = ta.getInt(R.styleable.SlideGroup_custom_layout_gravity, -1);
            ta.recycle();
        }

        public LayoutParams(int width, int height) {
            this(width, height, -1);
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
            this.gravity = gravity;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }
    }

    private PullDownListener pullDownListener;

    public void setPullDownListener(PullDownListener pullDownListener) {
        this.pullDownListener = pullDownListener;
    }

    public interface PullDownListener {
        public void onPullDown();
    }
}
