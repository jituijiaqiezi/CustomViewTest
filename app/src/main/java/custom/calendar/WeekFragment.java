package custom.calendar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekFragment extends Fragment {

    LinearLayout llWeek;
    RecyclerView recyclerViewWeek;
    List<String> weeks;

    public WeekFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        llWeek = (LinearLayout) rootView.findViewById(R.id.ll_week);
        weeks = new ArrayList<String>() {{
            add("周日");
            add("周一");
            add("周二");
            add("周三");
            add("周四");
            add("周五");
            add("周六");
        }};
        recyclerViewWeek = (RecyclerView) rootView.findViewById(R.id.recyclerView_week);
        recyclerViewWeek.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerViewWeek.setAdapter(new WeekAdapter(weeks));
    }

}
