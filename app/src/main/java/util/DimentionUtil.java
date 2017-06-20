package util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by linchenpeng on 2017/6/15.
 */

public class DimentionUtil {
    public static float sp2px(Context context,float value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,value,context.getResources().getDisplayMetrics());
    }
    public static float dp2px(Context context,float value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,context.getResources().getDisplayMetrics());
    }
}
