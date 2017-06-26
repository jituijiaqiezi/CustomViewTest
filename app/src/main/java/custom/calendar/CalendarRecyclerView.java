package custom.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class CalendarRecyclerView extends RecyclerView {
    public CalendarRecyclerView(Context context) {
        super(context);
    }

    public CalendarRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }*/
}
