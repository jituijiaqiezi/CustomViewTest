package util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by linchenpeng on 2017/6/15.
 */

public class DimensionUtil {
    public static float sp2px(Context context,float value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,value,context.getResources().getDisplayMetrics());
    }
    public static float dp2px(Context context,float value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,context.getResources().getDisplayMetrics());
    }
    public static int dip2px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5F);
    }
    public static int screenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    public static int screenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
