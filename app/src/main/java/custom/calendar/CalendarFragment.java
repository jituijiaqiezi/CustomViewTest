package custom.calendar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.List;

import util.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment implements TimeSelectView.OnCustomTouchEvent {
    private static final String TAG = CalendarFragment.class.getSimpleName();
    CalendarScrollView scrollView;

    LinearLayout llWeek;
    RecyclerView recyclerViewWeek;
    List<String> weeks;

    RecyclerView recyclerViewContent;
    List<Integer> contents;
    List<String> times;
    RecyclerView recyclerViewTime;

    ViewPager viewPager;
    TimeSelectView timeSelectView;
    int[] contentScreenLocations = new int[2];
    int[] contentParentLocations = new int[2];

    public CalendarFragment() {
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public void setTimeSelectView(TimeSelectView timeSelectView) {
        this.timeSelectView = timeSelectView;
        this.timeSelectView.setOnCustomTouchEvent(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        init(view);
        return view;
    }

    private void init(final View view) {
        llWeek=(LinearLayout)view.findViewById(R.id.ll_week);
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

        contents = new ArrayList<>();
        for (int i = 0; i < 24 * 7; i++) {
            contents.add(i);
        }

        recyclerViewContent = (RecyclerView) view.findViewById(R.id.recyclerView_content);
        recyclerViewContent.setLayoutManager(new GridLayoutManager(getContext(), 7));
        CalendarContentAdapter contentAdapter = new CalendarContentAdapter(contents);
        contentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int[] locations = new int[2];
                view.getLocationOnScreen(locations);
                Log.i(TAG, "父view在屏幕中的位置:" + contentScreenLocations[0] + "*" + contentScreenLocations[1] + ";" +
                        "父view在绘图区的位置:"+contentParentLocations[0] +"*"+contentParentLocations[1]+";"+
                        "在屏幕中的位置:" + locations[0] + "*" + locations[1]);
                Log.i(TAG,"屏幕:"+view.getX()+"*"+view.getY());
                Log.i(TAG,"周高度:"+recyclerViewWeek.getHeight()+",周父view高度:"+llWeek.getHeight());
                Log.i(TAG,"每个方框的宽度:"+view.getWidth());
                if (timeSelectView != null) {
                    timeSelectView.drawSelectArea(locations[0], locations[1] - contentScreenLocations[1]+llWeek.getHeight(), view.getWidth(), view.getHeight(), recyclerViewTime.getWidth(), llWeek.getHeight());
                }
            }
        });
        recyclerViewContent.setAdapter(contentAdapter);
        recyclerViewContent.post(new Runnable() {
            @Override
            public void run() {
                recyclerViewContent.getLocationOnScreen(contentScreenLocations);
                contentParentLocations[0]= (int) recyclerViewContent.getX();
                contentParentLocations[1]= (int) recyclerViewContent.getY();
            }
        });

        times = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            times.add(StringUtils.getTimeString(i) + "点");
        }
        recyclerViewTime = (RecyclerView) view.findViewById(R.id.recyclerView_time);
        recyclerViewTime.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTime.setAdapter(new CalendarTimeAdapter(getContext(), times));
        recyclerViewTime.addItemDecoration(new TimeItemDecoration(getContext()));

    }


    @Override
    public void disallowInterceptTouchEvent(boolean disallow) {
        scrollView.requestDisallowInterceptTouchEvent(disallow);
        viewPager.requestDisallowInterceptTouchEvent(disallow);
    }

    @Override
    public boolean onScroll(boolean up) {
        scrollView.smoothScrollBy(0, up ? -30 : 30);
        return scrollView.canScrollVertically(up ? -1 : 1);

    }
}
