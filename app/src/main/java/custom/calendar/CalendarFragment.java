package custom.calendar;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.List;

import util.DimensionUtil;
import util.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    RecyclerView recyclerViewTime;
    RecyclerView recyclerViewContent;

    List<String> times;
    List<Integer> contents;
    float topMargin;

    public CalendarFragment() {
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
        topMargin = getResources().getDimensionPixelSize(R.dimen.time_margin);
        times = new ArrayList<>();
        contents = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            times.add(StringUtils.getTimeString(i) + "点");
        }
        for (int i = 0; i < 24*7; i++) {
            contents.add(i);
        }
        Log.i("LCPC大小", contents.size() + "");
        recyclerViewTime = (CalendarRecyclerView) view.findViewById(R.id.recyclerView_time);
        recyclerViewTime.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTime.setAdapter(new CalendarTimeAdapter(getContext(), times));
        recyclerViewTime.addItemDecoration(new TimeItemDecoration());

        recyclerViewContent = (CalendarRecyclerView) view.findViewById(R.id.recyclerView_content);
        recyclerViewContent.setLayoutManager(new GridLayoutManager(getContext(), 7));
        CalendarContentAdapter contentAdapter=new CalendarContentAdapter(contents);
        contentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(),"点击"+position,Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewContent.setAdapter(contentAdapter);
        recyclerViewContent.addItemDecoration(new ContentItemDecoration());

    }

    class TimeItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildLayoutPosition(view) != 0) {
                outRect.top = (int) topMargin;
            } else {
                outRect.top = 0;
            }
        }
    }

    class ContentItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            Log.i("LCPConPosition", position + "");
            outRect.left = (int) DimensionUtil.dp2px(getContext(), 2);
            if (parent.getChildLayoutPosition(view) % 7 == 0)
                outRect.left = 0;
        }
    }

    class CalendarTimeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        public CalendarTimeAdapter(Context context, @Nullable List<String> data) {
            super(R.layout.item_time, data);

        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.text, item);
        }
    }

    class CalendarContentAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

        public CalendarContentAdapter(List<Integer> data) {
            super(R.layout.item_content, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Integer item) {

        }
    }

}
