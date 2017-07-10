package custom.region;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by linchenpeng on 2017/7/7.
 */

public class RegionClickView extends BaseView{
    Region circleRegion;
    Path circlePath;

    public RegionClickView(Context context){
        super(context);
    }
    public RegionClickView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public RegionClickView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    public void initData() {
        super.initData();
        mDefaultPaint.setColor(0xFF4E5268);
        circlePath=new Path();
        circleRegion=new Region();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //circlePath.addCircle(w/2,h/2,300,Path.Direction.CW);
        circlePath.moveTo(w/2,h/2);
        circlePath.quadTo(w/2+100,h/2-100,w/2+300,h/2);
        Region globalRegion=new Region(-w,-h,w,h);
        circleRegion.setPath(circlePath,globalRegion);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x=(int)event.getX();
                int y=(int)event.getY();
                if(circleRegion.contains(x,y)){
                    Toast.makeText(getContext(),"圆被点击",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(circlePath, mDefaultPaint);
    }
}
