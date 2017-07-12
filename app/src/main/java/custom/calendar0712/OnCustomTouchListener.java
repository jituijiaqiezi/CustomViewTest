package custom.calendar0712;

/**
 * Created by linchenpeng on 2017/7/11.
 */

public interface OnCustomTouchListener {
    void disallowInterceptTouchEvent(boolean disallow);

    boolean onScrollVertical(boolean up);

    boolean onScrollHorizontal(boolean left);
}
