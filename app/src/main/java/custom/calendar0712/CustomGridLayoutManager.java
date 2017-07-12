package custom.calendar0712;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by linchenpeng on 2017/7/11.
 */

public class CustomGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled=true;

    public CustomGridLayoutManager(Context context, int spanCount, boolean isScrollEnabled) {
        super(context, spanCount);
        this.isScrollEnabled = isScrollEnabled;
    }

    public CustomGridLayoutManager(Context context, int spanCount) {
        super(context,spanCount);
    }

    public void setScrollEnabled(boolean isScrollEnabled){
        this.isScrollEnabled=isScrollEnabled;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled&&super.canScrollVertically();
    }
}
