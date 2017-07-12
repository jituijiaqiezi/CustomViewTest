package custom.calendar0712;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by linchenpeng on 2017/7/12.
 */

public class RegionView extends View {
    Path topCirclePath,bottomCirclePath,squarePath;
    Region topRegion,bottomRegion,squareRegion;
    public RegionView(Context context) {
        super(context);
        init();
    }

    public RegionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){

    }

    /*public void reLayout(int x, int y, int width, int height, int marginLeft, int marginTop) {
        this.marginLeft = marginLeft;
        blockWidth = width;
        minWidth = (int) (blockWidth / 3);
        blockHeight = height;
        maxWidth = (int) (7 * blockWidth);
        maxHeight = (int) (24 * blockHeight);
        minHeight = (int) (2 * blockHeight / 3);
        this.marginTop = getResources().getDimensionPixelSize(R.dimen.time_height_half) + marginTop;
        reLayout(x, y,  x + width,  y + height);
    }

    private void reLayout(int left, int top, int right, int bottom) {
        int count = (int) Math.ceil((right - left) / blockWidth);
        this.firstTop = top;
        this.lastBottom = bottom;
        if (count > 1) {
            top = marginTop;
            bottom = maxHeight + marginTop;
        }
        layout(left, top, right, bottom);
        invalidate();
    }*/

}
