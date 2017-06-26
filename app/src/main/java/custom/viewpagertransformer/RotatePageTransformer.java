package custom.viewpagertransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by linchenpeng on 2017/6/23.
 */

public class RotatePageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            page.setRotation(0);
        } else if (position < 0) {
            page.setPivotX(0);
            page.setPivotY(0);
            page.setRotationY(-10*position);
        } else if (position <= 1) {
            page.setPivotX(page.getMeasuredWidth());
            page.setPivotY(0);
            //page.setRotation(20 * position);
            page.setRotationY(-10 * position);
        } else {
            page.setRotationY(0);
        }
    }
}
