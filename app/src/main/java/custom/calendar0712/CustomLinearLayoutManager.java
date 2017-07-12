package custom.calendar0712;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by linchenpeng on 2017/7/11.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled=true;
    public CustomLinearLayoutManager(Context context) {
        super(context);
    }
    public CustomLinearLayoutManager(Context context,boolean isScrollEnabled) {
        super(context);
        this.isScrollEnabled=isScrollEnabled;
    }
    public void setScrollEnabled(boolean isScrollEnabled){
        this.isScrollEnabled=isScrollEnabled;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled&&super.canScrollVertically();
    }
}
