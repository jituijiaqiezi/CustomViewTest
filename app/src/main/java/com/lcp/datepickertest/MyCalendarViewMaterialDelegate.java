package com.lcp.datepickertest;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;

@RequiresApi(api = Build.VERSION_CODES.N)
class MyCalendarViewMaterialDelegate extends MyCalendarView.AbstractCalendarViewDelegate {
    private final MyDayPickerView mDayPickerView;

    private MyCalendarView.OnDateChangeListener mOnDateChangeListener;

    public MyCalendarViewMaterialDelegate(MyCalendarView delegator, Context context, AttributeSet attrs,
                                          int defStyleAttr, int defStyleRes) {
        super(delegator, context);

        mDayPickerView = new MyDayPickerView(context, attrs, defStyleAttr, defStyleRes);
        mDayPickerView.setOnDaySelectedListener(mOnDaySelectedListener);

        delegator.addView(mDayPickerView);
    }

    @Override
    public void setWeekDayTextAppearance(@StyleRes int resId) {
        mDayPickerView.setDayOfWeekTextAppearance(resId);
    }

    @StyleRes
    @Override
    public int getWeekDayTextAppearance() {
        return mDayPickerView.getDayOfWeekTextAppearance();
    }

    @Override
    public void setDateTextAppearance(@StyleRes int resId) {
        mDayPickerView.setDayTextAppearance(resId);
    }

    @StyleRes
    @Override
    public int getDateTextAppearance() {
        return mDayPickerView.getDayTextAppearance();
    }

    @Override
    public void setMinDate(long minDate) {
        mDayPickerView.setMinDate(minDate);
    }

    @Override
    public long getMinDate() {
        return mDayPickerView.getMinDate();
    }

    @Override
    public void setMaxDate(long maxDate) {
        mDayPickerView.setMaxDate(maxDate);
    }

    @Override
    public long getMaxDate() {
        return mDayPickerView.getMaxDate();
    }

    @Override
    public void setFirstDayOfWeek(int firstDayOfWeek) {
        mDayPickerView.setFirstDayOfWeek(firstDayOfWeek);
    }

    @Override
    public int getFirstDayOfWeek() {
        return mDayPickerView.getFirstDayOfWeek();
    }

    @Override
    public void setDate(long date) {
        mDayPickerView.setDate(date, true);
    }

    @Override
    public void setDate(long date, boolean animate, boolean center) {
        mDayPickerView.setDate(date, animate);
    }

    @Override
    public long getDate() {
        return mDayPickerView.getDate();
    }

    @Override
    public void setOnDateChangeListener(MyCalendarView.OnDateChangeListener listener) {
        mOnDateChangeListener = listener;
    }

    private final MyDayPickerView.OnDaySelectedListener mOnDaySelectedListener = new MyDayPickerView.OnDaySelectedListener() {
        @Override
        public void onDaySelected(MyDayPickerView view, Calendar day) {
            if (mOnDateChangeListener != null) {
                final int year = day.get(Calendar.YEAR);
                final int month = day.get(Calendar.MONTH);
                final int dayOfMonth = day.get(Calendar.DAY_OF_MONTH);
                mOnDateChangeListener.onSelectedDayChange(mDelegator, year, month, dayOfMonth);
            }
        }
    };
}
