package custom.calendar0712;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcp.customviewtest.R;

class ContentItemDecoration extends RecyclerView.ItemDecoration {

    int bottomMargin;

    public ContentItemDecoration(Context context) {
        bottomMargin=context.getResources().getDimensionPixelSize(R.dimen.time_height);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if(position>=23*7)
            outRect.bottom=bottomMargin;
        else
            outRect.bottom=0;
    }
}