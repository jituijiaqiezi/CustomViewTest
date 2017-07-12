package custom.calendar0712;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcp.customviewtest.R;

class TimeItemDecoration extends RecyclerView.ItemDecoration {

    private int topMargin, bottomMargin;

    TimeItemDecoration(Context context) {
        topMargin = context.getResources().getDimensionPixelSize(R.dimen.time_margin);
        bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.time_height_half);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (position != 0)
            outRect.top =  topMargin;
        else
            outRect.top = 0;
        if (position == 24)
            outRect.bottom =  bottomMargin;
        else
            outRect.bottom = 0;
    }
}