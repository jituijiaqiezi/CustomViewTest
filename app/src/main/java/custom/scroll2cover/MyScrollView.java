package custom.scroll2cover;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by linchenpeng on 2017/6/22.
 */

public class MyScrollView extends ScrollView {
    private OnScrolledListener listener = new OnScrolledListener() {
        @Override
        public void scroll(int y) {

        }
    };

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        listener.scroll(getScrollY());
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setListener(OnScrolledListener listener) {
        this.listener = listener;
    }
}
