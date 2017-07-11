package custom.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by linchenpeng on 2017/7/11.
 */

public class WeekFragmentAdapter extends FragmentStatePagerAdapter{

    public WeekFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        WeekFragment fragment = new WeekFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 100;
    }
}
