package custom.calendar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment implements TimeSelectViewGroup.DisallowInterceptTouchEvent {

    ScrollView scrollView;
    TimeSelectViewGroup timeSelectViewGroup;

    RecyclerView recyclerViewWeek;
    List<String> weeks;

    ViewPager viewPager;


    public CalendarFragment() {
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        init(view);
        return view;
    }

    private void init(final View view) {
        scrollView = (CalendarScrollView) view.findViewById(R.id.scrollView);

        weeks = new ArrayList<String>() {{
            add("周日");
            add("周一");
            add("周二");
            add("周三");
            add("周四");
            add("周五");
            add("周六");
        }};
        recyclerViewWeek = (RecyclerView) view.findViewById(R.id.recyclerView_week);
        recyclerViewWeek.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerViewWeek.addItemDecoration(new WeekItemDecoration(getContext()));
        recyclerViewWeek.setAdapter(new CalendarWeekAdapter(weeks));

        timeSelectViewGroup = (TimeSelectViewGroup) view.findViewById(R.id.time_select_view);
        timeSelectViewGroup.setDisallowInterceptTouchEvent(this);

    }

    @Override
    public void disallowInterceptTouchEvent(boolean yes) {
        scrollView.requestDisallowInterceptTouchEvent(yes);
        viewPager.requestDisallowInterceptTouchEvent(yes);
    }
}
