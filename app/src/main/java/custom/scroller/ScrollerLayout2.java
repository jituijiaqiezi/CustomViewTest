package custom.scroller;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by linchenpeng on 2017/7/5.
 */

public class ScrollerLayout2 extends LinearLayout{
    private Scroller mScroller;
    private BallView ballView;
    private int realHeight;
    public ScrollerLayout2(Context context) {
        this(context,null);
    }

    public ScrollerLayout2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller=new Scroller(context,new BounceInterpolator());
    }

    public void smoothScrollTo(){
        ballView=(BallView)getChildAt(0);
        int scrollX=getScrollX();
        realHeight=getHeight()-2*ballView.getRadius();
        mScroller.startScroll(scrollX,0,0,realHeight,10000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
