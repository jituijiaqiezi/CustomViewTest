package custom.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

class CalendarFragmentAdapter extends FragmentStatePagerAdapter {

    ViewPager viewPager;

    public CalendarFragmentAdapter(FragmentManager fm, ViewPager viewPager) {
        super(fm);
        this.viewPager=viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setViewPager(viewPager);
        return fragment;
    }

    @Override
    public int getCount() {
        return 100;
    }
}
