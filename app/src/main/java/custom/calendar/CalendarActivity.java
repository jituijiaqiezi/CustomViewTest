package custom.calendar;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.lcp.customviewtest.R;

public class CalendarActivity extends AppCompatActivity {

    private final String TAG = CalendarActivity.class.getSimpleName();

    TextView textIndex;

    ViewPager viewPagerContent;
    ContentFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        init();
    }

    private void init() {
        textIndex=(TextView)findViewById(R.id.text_index);

        viewPagerContent = (ViewPager) findViewById(R.id.viewpager_content);
        adapter = new ContentFragmentAdapter(getSupportFragmentManager(),viewPagerContent);
        viewPagerContent.setAdapter(adapter);
        viewPagerContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {
                //timeSelectView.scrollByViewPager(positionOffsetPixels,0);
                Log.i(TAG,"ViewPager滑动:"+position+"，"+positionOffset+","+positionOffsetPixels);

            }

            @Override
            public void onPageSelected(final int position) {
                Log.i(TAG,"ViewPager选择:"+position);
                textIndex.post(new Runnable() {
                    @Override
                    public void run() {
                        textIndex.setText(String.valueOf(position));
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ViewPagerScroller scroller=new ViewPagerScroller(this);
        scroller.setScrollDuration(1000);
        scroller.initViewPagerScroll(viewPagerContent);
    }
}
