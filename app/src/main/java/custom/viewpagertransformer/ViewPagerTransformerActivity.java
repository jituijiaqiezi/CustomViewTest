package custom.viewpagertransformer;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerTransformerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private int[]mImgIds=new int[]{R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4};
    private List<ImageView> mImageViews=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_transformer);
        init();
    }
    private void init(){
        for(int imgId:mImgIds){
            ImageView imageView=new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgId);
            mImageViews.add(imageView);
        }
        mViewPager=(ViewPager)findViewById(R.id.viewpager);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageViews.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mImageViews.get(position));
                return mImageViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });
        mViewPager.setPageTransformer(true,new RotatePageTransformer());
    }
}
