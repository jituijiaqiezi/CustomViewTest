package custom.floatingbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by linchenpeng on 2017/6/15.
 */

public class FloatingView extends View {

    private static final String TAG = FloatingView.class.getSimpleName();
    public FloatingView(Context context) {
        this(context, null);
    }

    public FloatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
    }


}
