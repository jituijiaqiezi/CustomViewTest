package custom.calendar0712;

import android.graphics.Point;

/**
 * Created by linchenpeng on 2017/7/11.
 */

public interface OnCircleTouchListener extends OnCustomTouchListener {
    Point reLayoutTop(int deltaX, int deltaY);

    Point reLayoutBottom(int deltaX, int deltaY);

    boolean minHeight();
}
