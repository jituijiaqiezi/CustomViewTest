package custom.indicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.lcp.datepickertest.R;

/**
 * Created by linchenpeng on 17/6/16.
 */

public class UnderlinePagerIndicator extends View implements PageIndicator {
    private static final int INVALID_POINTER = -1;
    private static final int FADE_FRAME_MS = 30;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean mFades;
    private int mFadeDelay;
    private int mFadeLength;
    private int mFadeBy;
    private final Runnable mFadeRunnable = new Runnable() {
        @Override
        public void run() {
            if (!mFades)
                return;
            final int alpha = Math.max(mPaint.getAlpha() - mFadeBy, 0);
            mPaint.setAlpha(alpha);
            invalidate();
            if (alpha > 0) {
                postDelayed(this, FADE_FRAME_MS);
            }
        }
    };
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;
    private int mScrollState;
    private int mCurrentPage;
    private float mPositionOffset;
    private int mTouchSlop;
    private float mLastMotionX = -1;
    private int mActivePointerId = INVALID_POINTER;
    private boolean mIsDragging;

    public UnderlinePagerIndicator(Context context) {
        this(context, null);
    }

    public UnderlinePagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.vpiUnderlinePageIndicatorStyle);
    }

    public UnderlinePagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) return;

        final Resources res = getResources();
        final boolean defaultFades = res.getBoolean(R.bool.default_underline_indicator_fades);
        final int defaultFadeDelay = res.getInteger(R.integer.default_underline_indicator_fade_delay);
        final int defaultFadeLength = res.getInteger(R.integer.default_underline_indicator_fade_length);
        final int defaultSelectedColor = res.getColor(R.color.default_underline_indicator_selected_color);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UnderlinePagerIndicator, defStyle, 0);
        setFades(array.getBoolean(R.styleable.UnderlinePagerIndicator_fades, defaultFades));
        setSelectedColor(array.getColor(R.styleable.UnderlinePagerIndicator_selectedColor, defaultSelectedColor));
        setFadeDelay(array.getInteger(R.styleable.UnderlinePagerIndicator_fadeDelay, defaultFadeDelay));
        setFadeLength(array.getInteger(R.styleable.UnderlinePagerIndicator_fadeLength, defaultFadeLength));

        Drawable background = array.getDrawable(R.styleable.UnderlinePagerIndicator_android_background);
        if (background != null)
            setBackgroundDrawable(background);

        array.recycle();
        mTouchSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();
    }

    public boolean getFades() {
        return mFades;
    }

    public void setFades(boolean fades) {
        if (fades != mFades) {
            mFades = fades;
            if (fades)
                post(mFadeRunnable);
            else {
                removeCallbacks(mFadeRunnable);
                mPaint.setAlpha(0xFF);
                invalidate();
            }
        }
    }

    public int getFadeDelay() {
        return mFadeDelay;
    }

    public void setFadeDelay(int fadeDelay) {
        mFadeDelay = fadeDelay;
    }

    public int getFadeLength() {
        return mFadeLength;
    }

    public void setFadeLength(int fadeLength) {
        mFadeLength = fadeLength;
        mFadeBy = 0xFF / (mFadeLength / FADE_FRAME_MS);
    }

    public int getSelectedColor() {
        return mPaint.getColor();
    }

    public void setSelectedColor(int selectedColor) {
        mPaint.setColor(selectedColor);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mViewPager == null) {
            return;
        }

        final int count = mViewPager.getAdapter().getCount();
        if (count == 0)
            return;

        if (mCurrentPage >= count) {
            setCurrentItem(count - 1);
            return;
        }
        final int paddingLeft = getPaddingLeft();
        final float pageWidth = (getWidth() - paddingLeft - getPaddingRight()) / (1f * count);
        final float left = paddingLeft + pageWidth * (mCurrentPage + mPositionOffset);
        final float right = left + pageWidth;
        final float top = getPaddingTop();
        final float bottom = getHeight() - getPaddingBottom();
        canvas.drawRect(left, top, right, bottom, mPaint);
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        if (mViewPager == viewPager)
            return;
        if (mViewPager != null) {
            mViewPager.clearOnPageChangeListeners();
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        invalidate();
        post(new Runnable() {
            @Override
            public void run() {
                if (mFades)
                    post(mFadeRunnable);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (super.onTouchEvent(event))
            return true;
        if ((mViewPager == null) || (mViewPager.getAdapter().getCount() == 0)) {
            return false;
        }

        final int action = event.getAction() & MotionEventCompat.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = event.getPointerId(0);
                mLastMotionX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                final int activePointerIndex = event.findPointerIndex(mActivePointerId);
                final float x = event.getX(activePointerIndex);
                final float deltaX = x - mLastMotionX;

                if (!mIsDragging) {
                    if (Math.abs(deltaX) > mTouchSlop)
                        mIsDragging = true;
                }
                if (mIsDragging) {
                    mLastMotionX = x;
                    if (mViewPager.isFakeDragging() || mViewPager.beginFakeDrag())
                        mViewPager.fakeDragBy(deltaX);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!mIsDragging) {
                    final int count = mViewPager.getAdapter().getCount();
                    final int width = getWidth();
                    final float halfWidth = width / 2f;
                    final float sixthWidth = width / 6f;
                    if ((mCurrentPage > 0) && (event.getX() < halfWidth - sixthWidth)) {
                        if (action != MotionEvent.ACTION_CANCEL)
                            mViewPager.setCurrentItem(mCurrentPage - 1);
                        return true;
                    } else if ((mCurrentPage < count - 1) && (event.getX() > halfWidth + sixthWidth)) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            mViewPager.setCurrentItem(mCurrentPage + 1);
                        }
                        return true;
                    }
                }
                mIsDragging = false;
                mActivePointerId = INVALID_POINTER;
                if (mViewPager.isFakeDragging())
                    mViewPager.endFakeDrag();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                final int index = MotionEventCompat.getActionIndex(event);
                mLastMotionX = event.getX(index);
                mActivePointerId = event.getPointerId(index);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                final int pointerIndex = event.getActionIndex();
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                mLastMotionX = event.getX(event.findPointerIndex(mActivePointerId));
                break;
        }

        return true;
    }

    @Override
    public void setViewPager(ViewPager viewPager, int initialPosition) {
        setViewPager(viewPager);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null)
            throw new IllegalStateException("ViewPager has not been bound.");
        mViewPager.setCurrentItem(item);
        mCurrentPage = item;
        invalidate();
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        invalidate();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPage = position;
        mPositionOffset = positionOffset;
        if (mFades) {
            if (positionOffsetPixels > 0) {
                removeCallbacks(mFadeRunnable);
                mPaint.setAlpha(0xFF);
            } else if (mScrollState != ViewPager.SCROLL_STATE_DRAGGING) {
                postDelayed(mFadeRunnable, mFadeDelay);
            }
        }
        invalidate();
        if (mListener != null)
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentPage = position;
            mPositionOffset = 0;
            invalidate();
            mFadeRunnable.run();
        }
        if (mListener != null)
            mListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
        if (mListener != null)
            mListener.onPageScrollStateChanged(state);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPage = savedState.currentPage;
        requestLayout();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = mCurrentPage;
        return savedState;
    }

    static class SavedState extends BaseSavedState {

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int currentPage;

        public SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(currentPage);
        }
    }
}
