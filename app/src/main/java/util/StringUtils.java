package util;

import android.text.TextUtils;

/**
 * Created by linchenpeng on 2017/6/26.
 */

public class StringUtils {
    public static String getTimeString(String time) {
        if (time == null)
            return "00";
        time = time.trim();
        if (TextUtils.isEmpty(time))
            return "00";
        if (time.length() < 2)
            return "0" + time;
        return time;
    }
    public static String getTimeString(int time){
        return getTimeString(String.valueOf(time));
    }
}
