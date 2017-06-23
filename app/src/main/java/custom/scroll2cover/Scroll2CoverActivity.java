package custom.scroll2cover;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lcp.customviewtest.R;

public class Scroll2CoverActivity extends AppCompatActivity {

    private MyScrollView mScrollView;
    private ViewPager mViewPager;
    public static int DISPLAYW = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll2_cover);
        init();
    }

    private void init() {
        DISPLAYW = getResources().getDisplayMetrics().widthPixels;
        mScrollView = (MyScrollView) findViewById(R.id.scroll);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mViewPager.setLayoutParams(new RelativeLayout.LayoutParams(DISPLAYW, DISPLAYW));
        mScrollView.setListener(new OnScrolledListener() {
            @Override
            public void scroll(int y) {
                Log.i("Scroll", y+"");
                mViewPager.scrollTo(mViewPager.getCurrentItem() * DISPLAYW, -y);
            }
        });
        mViewPager.setAdapter(new ImageAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mScrollView.scrollTo(0, 0);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ImageAdapter extends PagerAdapter {
        private int[] imgs = {R.mipmap.ic_launcher, android.R.drawable.ic_menu_rotate};

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(Scroll2CoverActivity.this);
            imageView.setMinimumHeight(DISPLAYW);
            imageView.setMinimumWidth(DISPLAYW);
            imageView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgs[position % 2]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }
}
