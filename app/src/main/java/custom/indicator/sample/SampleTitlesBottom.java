package custom.indicator.sample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.lcp.customviewtest.R;

import custom.indicator.TitlePageIndicator;


public class SampleTitlesBottom extends BaseSampleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_titles_bottom);

        mAdapter = new TestFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }
}