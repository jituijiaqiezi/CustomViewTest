package custom.calendar;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements OnCustomTouchListener {

    private final String TAG = CalendarActivity.class.getSimpleName();

    TextView textIndex;
    LinearLayout llWeek;
    RecyclerView recyclerViewWeek;
    List<String> weeks;

    ViewPager viewPagerContent;
    ContentFragmentAdapter adapter;
    TimeSelectView timeSelectView;
    VerticalScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        init();
    }

    private void init() {
        textIndex=(TextView)findViewById(R.id.text_index);
        llWeek = (LinearLayout) findViewById(R.id.ll_week);
        weeks = new ArrayList<String>() {{
            add("周日");
            add("周一");
            add("周二");
            add("周三");
            add("周四");
            add("周五");
            add("周六");
        }};
        recyclerViewWeek = (RecyclerView) findViewById(R.id.recyclerView_week);
        recyclerViewWeek.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerViewWeek.setAdapter(new WeekAdapter(weeks));

        scrollView = (VerticalScrollView) findViewById(R.id.scrollView);
        viewPagerContent = (ViewPager) findViewById(R.id.viewpager_content);
        timeSelectView = (TimeSelectView) findViewById(R.id.time_select_view);
        timeSelectView.setOnTouchListener(this);
        adapter = new ContentFragmentAdapter(getSupportFragmentManager(), timeSelectView);
        viewPagerContent.setAdapter(adapter);
        viewPagerContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {
                textIndex.post(new Runnable() {
                    @Override
                    public void run() {
                        textIndex.setText(String.valueOf(position));
                    }
                });
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ViewPagerScroller scroller=new ViewPagerScroller(this);
        scroller.setScrollDuration(1000);
        scroller.initViewPagerScroll(viewPagerContent);
    }

    public ContentFragment getCurrentFragment() {
        return (ContentFragment) adapter.instantiateItem(viewPagerContent, viewPagerContent.getCurrentItem());
    }

    @Override
    public void disallowInterceptTouchEvent(boolean disallow) {
        scrollView.requestDisallowInterceptTouchEvent(disallow);
        viewPagerContent.requestDisallowInterceptTouchEvent(disallow);
    }

    @Override
    public boolean onScrollVertical(boolean up) {
        Log.i(TAG, "滑动方向:" + (up ? "向上" : "向下"));
        boolean canScroll = scrollView.canScrollVertically(up ? -1 : 1);
        scrollView.smoothScrollBy(0, up ? -30 : 30);
        Log.i(TAG, "是否可以滑动:" + canScroll);
        return canScroll;
    }

    @Override
    public boolean onScrollHorizontal(boolean left) {
        int index = viewPagerContent.getCurrentItem();
        if (left && index == 0 || !left && index == adapter.getCount() - 1)
            return false;
        else {
            viewPagerContent.setCurrentItem(left ? --index : ++index);
            return true;
        }
    }
}
