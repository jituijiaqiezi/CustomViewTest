package custom.calendar;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.List;

import util.StringUtils;

public class TextLineActivityOld extends AppCompatActivity {

    ViewPager viewPager;
    RecyclerView recyclerViewWeek;
    RecyclerView recyclerViewTime;
    List<String> times;
    List<String> weeks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_line);
        init();
    }
    private void init(){
        weeks=new ArrayList<String>(){{
            add("周日");
            add("周一");
            add("周二");
            add("周三");
            add("周四");
            add("周五");
            add("周六");
        }};
        recyclerViewWeek=(RecyclerView)findViewById(R.id.recyclerView_week);
        recyclerViewWeek.setLayoutManager(new GridLayoutManager(this,7));
        recyclerViewWeek.addItemDecoration(new WeekItemDecoration(this));
        recyclerViewWeek.setAdapter(new CalendarWeekAdapter(weeks));

        viewPager=(ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new CalendarFragmentAdapter(getSupportFragmentManager(),viewPager));

        times = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            times.add(StringUtils.getTimeString(i) + "点");
        }
        recyclerViewTime = (CalendarRecyclerView) findViewById(R.id.recyclerView_time);
        recyclerViewTime.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTime.setAdapter(new CalendarTimeAdapter(this, times));
        recyclerViewTime.addItemDecoration(new TimeItemDecoration(this));
    }





}
