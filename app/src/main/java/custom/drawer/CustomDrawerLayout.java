package custom.drawer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by linchenpeng on 2017/7/7.
 */

public class CustomDrawerLayout extends RelativeLayout {
    public CustomDrawerLayout(Context context) {
        super(context);
        init();
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

    }
}
