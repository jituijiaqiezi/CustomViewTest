package custom.calendar;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lcp.customviewtest.R;

public class TextLineActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_line);
        init();
    }
    private void init(){
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new CalendarFragmentAdapter(getSupportFragmentManager(),viewPager));
    }
}
