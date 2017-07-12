package custom.calendar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.List;

import util.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment implements OnCustomTouchListener {
    CalendarActivity calendarActivity;
    private static final String TAG = ContentFragment.class.getSimpleName();

    ViewPager viewPager;
    CalendarScrollView scrollView;
    RecyclerView recyclerViewContent;
    List<Integer> contents;
    List<String> times;
    RecyclerView recyclerViewTime;

    TimeSelectView timeSelectView;
    int index;


    public ContentFragment() {
    }

    public static ContentFragment newInstance(int index) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            index = getArguments().getInt("index", -1);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("index", index);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null)
            index = savedInstanceState.getInt("index", -1);
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        calendarActivity = (CalendarActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        init(view);
        return view;
    }

    private void init(final View view) {

        scrollView = (CalendarScrollView) view.findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                timeSelectView.removeView();
                return false;
            }
        });
        timeSelectView = (TimeSelectView) view.findViewById(R.id.time_select_view);
        timeSelectView.setOnCustomTouchListener(this);
        contents = new ArrayList<>();
        for (int i = 0; i < 24 * 7; i++) {
            contents.add(i);
        }

        recyclerViewContent = (RecyclerView) view.findViewById(R.id.recyclerView_content);
        recyclerViewContent.setLayoutManager(new CustomGridLayoutManager(getContext(), 7));
        ContentAdapter contentAdapter = new ContentAdapter(contents);
        contentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (timeSelectView != null) {
                    //timeSelectView.drawSelectArea(index,locations[0], locations[1] - contentScreenLocations[1], view.getWidth(), view.getHeight(), recyclerViewTime.getWidth(), 0);
                    timeSelectView.drawSelectArea(index, view, recyclerViewTime.getWidth());
                }
            }
        });
        recyclerViewContent.setAdapter(contentAdapter);
        //recyclerViewContent.addItemDecoration(new ContentItemDecoration(getContext()));

        times = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            times.add(StringUtils.getTimeString(i) + "点");
        }
        recyclerViewTime = (RecyclerView) view.findViewById(R.id.recyclerView_time);
        recyclerViewTime.setLayoutManager(new CustomLinearLayoutManager(getContext()));
        recyclerViewTime.setAdapter(new TimeAdapter(getContext(), times));
        recyclerViewTime.addItemDecoration(new TimeItemDecoration(getContext()));
        recyclerViewTime.setNestedScrollingEnabled(false);
        recyclerViewContent.setNestedScrollingEnabled(false);

    }

    public RecyclerView getRecyclerViewContent() {
        return recyclerViewContent;
    }


    @Override
    public void disallowInterceptTouchEvent(boolean disallow) {
        viewPager.requestDisallowInterceptTouchEvent(disallow);
        scrollView.requestDisallowInterceptTouchEvent(disallow);
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
        int index = viewPager.getCurrentItem();
        if (left && index == 0 || !left && index == viewPager.getAdapter().getCount() - 1)
            return false;
        else {
            viewPager.setCurrentItem(left ? --index : ++index);
            return true;
        }
    }
}
