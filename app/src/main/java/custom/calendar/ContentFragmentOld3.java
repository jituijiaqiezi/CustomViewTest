package custom.calendar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
public class ContentFragmentOld3 extends Fragment  {
    CalendarActivity calendarActivity;
    private static final String TAG = ContentFragmentOld3.class.getSimpleName();

    RecyclerView recyclerViewContent;
    List<Integer> contents;
    List<String> times;
    RecyclerView recyclerViewTime;

    TimeSelectView timeSelectView;
    int[] contentScreenLocations = new int[2];
    int[] contentParentLocations = new int[2];


    public ContentFragmentOld3() {
    }

    public void setTimeSelectView(TimeSelectView timeSelectView) {
        this.timeSelectView = timeSelectView;
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
                int[] locations = new int[2];
                view.getLocationOnScreen(locations);
                if (timeSelectView != null) {
                    timeSelectView.drawSelectArea(locations[0], locations[1] - contentScreenLocations[1], view.getWidth(), view.getHeight(), recyclerViewTime.getWidth(), 0);
                }
            }
        });
        recyclerViewContent.setAdapter(contentAdapter);
        recyclerViewContent.post(new Runnable() {
            @Override
            public void run() {
                recyclerViewContent.getLocationOnScreen(contentScreenLocations);
                contentParentLocations[0] = (int) recyclerViewContent.getX();
                contentParentLocations[1] = (int) recyclerViewContent.getY();
            }
        });
        //recyclerViewContent.setNestedScrollingEnabled(false);

        times = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            times.add(StringUtils.getTimeString(i) + "点");
        }
        recyclerViewTime = (RecyclerView) view.findViewById(R.id.recyclerView_time);
        recyclerViewTime.setLayoutManager(new CustomLinearLayoutManager(getContext()));
        recyclerViewTime.setAdapter(new TimeAdapter(getContext(), times));
        recyclerViewTime.addItemDecoration(new TimeItemDecoration(getContext()));
        //recyclerViewTime.setNestedScrollingEnabled(false);

    }
}
