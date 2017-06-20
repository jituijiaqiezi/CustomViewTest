package android.support.v4.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.android.internal.util.Predicate;
import com.lcp.datepickertest.MyDayPickerPagerAdapter;
import com.lcp.datepickertest.MySimpleMonthView;

import java.lang.reflect.Method;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.N)
class MyDayPickerViewPager extends ViewPager {
    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);

    public MyDayPickerViewPager(Context context) {
        this(context, null);
    }

    public MyDayPickerViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDayPickerViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyDayPickerViewPager(Context context, AttributeSet attrs, int defStyleAttr,
                                int defStyleRes) {
        super(context,attrs);
        //super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        populate();

        // Everything below is mostly copied from FrameLayout.
        int count = getChildCount();

        final boolean measureMatchParentChildren =
                MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY ||
                        MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;

        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
                childState = combineMeasuredStates(childState, child.getMeasuredState());
                if (measureMatchParentChildren) {
                    if (lp.width == LayoutParams.MATCH_PARENT ||
                            lp.height == LayoutParams.MATCH_PARENT) {
                        mMatchParentChildren.add(child);
                    }
                }
            }
        }

        // Account for padding too
        maxWidth += getPaddingLeft() + getPaddingRight();
        maxHeight += getPaddingTop() + getPaddingBottom();

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        // Check against our foreground's minimum height and width
        final Drawable drawable = getForeground();
        if (drawable != null) {
            maxHeight = Math.max(maxHeight, drawable.getMinimumHeight());
            maxWidth = Math.max(maxWidth, drawable.getMinimumWidth());
        }

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));

        count = mMatchParentChildren.size();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                final View child = mMatchParentChildren.get(i);

                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                final int childWidthMeasureSpec;
                final int childHeightMeasureSpec;

                if (lp.width == LayoutParams.MATCH_PARENT) {
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                            getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                            MeasureSpec.EXACTLY);
                } else {
                    childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                            getPaddingLeft() + getPaddingRight(),
                            lp.width);
                }

                if (lp.height == LayoutParams.MATCH_PARENT) {
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                            getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
                            MeasureSpec.EXACTLY);
                } else {
                    childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                            getPaddingTop() + getPaddingBottom(),
                            lp.height);
                }

                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }

        mMatchParentChildren.clear();
    }

    protected View findViewByPredicateTraversal(Predicate<View> predicate, View childToSkip) {
        try {
            if (predicate.apply(this)) {
                return this;
            }

            //通过反射获取响应的方法
            Method method=Class.forName(View.class.getName()).getDeclaredMethod("findViewByPredicate",Predicate.class);
            method.setAccessible(true);
            // Always try the selected view first.
            final MyDayPickerPagerAdapter adapter = (MyDayPickerPagerAdapter) getAdapter();
            final MySimpleMonthView current = adapter.getView(getCurrent());
            if (current != childToSkip && current != null) {
                //final View v = current.findViewByPredicate(predicate);
                final View v= (View) method.invoke(current,predicate);
                if (v != null) {
                    return v;
                }
            }

            final int len = getChildCount();

            for (int i = 0; i < len; i++) {
                final View child = getChildAt(i);

                if (child != childToSkip && child != current) {
                    //final View v = child.findViewByPredicate(predicate);
                    final View v= (View) method.invoke(child,predicate);
                    if (v != null) {
                        return v;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    public Object getCurrent() {
        final ItemInfo itemInfo = infoForPosition(getCurrentItem());
        return itemInfo == null ? null : itemInfo.object;
    }

}
