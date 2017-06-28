package custom.calendar;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import util.DimensionUtil;

class WeekItemDecoration extends RecyclerView.ItemDecoration {
    int left;

    public WeekItemDecoration(Context context) {
        left = DimensionUtil.dip2px(context, 2);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        Log.i("LCPConPosition", position + "");
        outRect.left = 0;
        if (parent.getChildLayoutPosition(view) % 7 == 0)
            outRect.left = 0;
    }
}