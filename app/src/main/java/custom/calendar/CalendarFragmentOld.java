package custom.calendar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragmentOld extends Fragment {

    RecyclerView recyclerViewContent;
    TimeSelectViewGroup timeSelectViewGroup;
    List<Integer> contents;

    public CalendarFragmentOld() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

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
                Toast.makeText(getContext(), "点击" + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewContent.setAdapter(contentAdapter);
        recyclerViewContent.addItemDecoration(new ContentItemDecoration(getContext()));

        timeSelectViewGroup = (TimeSelectViewGroup) view.findViewById(R.id.time_select_view);
        /*timeSelectViewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
                if (event.getAction() == MotionEvent.ACTION_UP)
                    scrollView.requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });*/

    }









}
