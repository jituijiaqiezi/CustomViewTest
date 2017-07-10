package custom.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

class CalendarFragmentAdapter extends FragmentStatePagerAdapter {

    ViewPager viewPager;
    TimeSelectView timeSelectView;

    public CalendarFragmentAdapter(FragmentManager fm, ViewPager viewPager,TimeSelectView timeSelectView) {
        super(fm);
        this.viewPager=viewPager;
        this.timeSelectView=timeSelectView;
    }

    @Override
    public Fragment getItem(int position) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setViewPager(viewPager);
        fragment.setTimeSelectView(timeSelectView);
        return fragment;
    }

    @Override
    public int getCount() {
        return 100;
    }
}
