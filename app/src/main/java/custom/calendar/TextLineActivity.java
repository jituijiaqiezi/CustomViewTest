package custom.calendar;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.List;

import util.DimensionUtil;

public class TextLineActivity extends AppCompatActivity {

    ViewPager viewPager;
    RecyclerView recyclerViewWeek;
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
        recyclerViewWeek.addItemDecoration(new WeekItemDecoration());
        recyclerViewWeek.setAdapter(new CalendarWeekAdapter(weeks));

        viewPager=(ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new CalendarFragmentAdapter(getSupportFragmentManager()));
    }

    class CalendarFragmentAdapter extends FragmentStatePagerAdapter{

        public CalendarFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new CalendarFragment();
        }

        @Override
        public int getCount() {
            return 100;
        }
    }
    class WeekItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            Log.i("LCPConPosition", position + "");
            outRect.left = DimensionUtil.dip2px(getApplicationContext(), 2);
            if (parent.getChildLayoutPosition(view) % 7 == 0)
                outRect.left = 0;
        }
    }

    class CalendarWeekAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public CalendarWeekAdapter(List<String> data) {
            super(R.layout.item_week, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.text,item);
        }
    }
}
