package custom.indicator;

import android.support.v4.view.ViewPager;

/**
 * Created by linchenpeng on 17/6/16.
 */

public interface PageIndicator extends ViewPager.OnPageChangeListener{
    void setViewPager(ViewPager view);

    void setViewPager(ViewPager view, int initialPosition);

    void setCurrentItem(int item);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    void notifyDataSetChanged();
}
