package custom.calendar;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import util.DimensionUtil;

class ContentItemDecoration extends RecyclerView.ItemDecoration {

    Context context;

    public ContentItemDecoration(Context context) {
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        Log.i("LCPConPosition", position + "");
        outRect.left = (int) DimensionUtil.dp2px(context, 2);
        if (parent.getChildLayoutPosition(view) % 7 == 0)
            outRect.left = 0;
    }
}