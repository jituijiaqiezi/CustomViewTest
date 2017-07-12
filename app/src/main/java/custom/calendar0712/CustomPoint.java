package custom.calendar0712;

import android.graphics.Point;

/**
 * Created by linchenpeng on 2017/6/29.
 */

public class CustomPoint extends Point {

    public CustomPoint(int x, int y) {
        super(x, y);
    }

    public CustomPoint(float x, float y) {
        this((int) x, (int) y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof CustomPoint) {
            Point temp = (Point) obj;
            return Math.abs(x - temp.x) < 5 && Math.abs(y - temp.y) < 5;
        }
        return false;
    }
}
