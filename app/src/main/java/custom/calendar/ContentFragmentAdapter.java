package custom.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

class ContentFragmentAdapter extends FragmentStatePagerAdapter {

    ViewPager viewPager;

    public ContentFragmentAdapter(FragmentManager fm,ViewPager viewPager) {
        super(fm);
        this.viewPager=viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        ContentFragment fragment = ContentFragment.newInstance(position);
        fragment.setViewPager(viewPager);
        return fragment;
    }

    @Override
    public int getCount() {
        return 100;
    }
}
