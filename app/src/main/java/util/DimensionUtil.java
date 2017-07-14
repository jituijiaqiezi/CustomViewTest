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

    public static int px2dip(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5F);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public static int getStatusBarHeightDP(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return px2dip(context, result);
    }

    public static int getActionBarSize(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static int getActionBarSizeDP(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return px2dip(context, actionBarHeight);
    }
}
