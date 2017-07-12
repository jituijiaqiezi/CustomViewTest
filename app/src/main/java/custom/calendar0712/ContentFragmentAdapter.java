package custom.calendar0712;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class ContentFragmentAdapter extends FragmentStatePagerAdapter {

    TimeSelectView timeSelectView;

    public ContentFragmentAdapter(FragmentManager fm, TimeSelectView timeSelectView) {
        super(fm);
        this.timeSelectView=timeSelectView;
    }

    @Override
    public Fragment getItem(int position) {
        ContentFragment fragment = ContentFragment.newInstance(position);
        fragment.setTimeSelectView(timeSelectView);
        return fragment;
    }

    @Override
    public int getCount() {
        return 100;
    }
}
