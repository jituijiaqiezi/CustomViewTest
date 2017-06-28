package custom.calendar;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcp.customviewtest.R;

class TimeItemDecoration extends RecyclerView.ItemDecoration {

    float topMargin;

    TimeItemDecoration(Context context) {
        topMargin = context.getResources().getDimensionPixelSize(R.dimen.time_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) != 0) {
            outRect.top = (int) topMargin;
        } else {
            outRect.top = 0;
        }
    }
}