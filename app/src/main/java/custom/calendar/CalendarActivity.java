package custom.calendar;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lcp.customviewtest.R;

public class CalendarActivity extends AppCompatActivity {

    ViewPager viewPager;
    TimeSelectView timeSelectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        init();
    }
    private void init(){
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        timeSelectView=(TimeSelectView)findViewById(R.id.time_select_view);
        viewPager.setAdapter(new CalendarFragmentAdapter(getSupportFragmentManager(),viewPager,timeSelectView));
    }
}
