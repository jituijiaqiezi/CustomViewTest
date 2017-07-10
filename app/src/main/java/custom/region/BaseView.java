package custom.region;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by linchenpeng on 2017/7/7.
 */

public class BaseView extends View{
    /**
     * the context of current view
     */
    protected Context mCurrentContext;

    /**
     * the width of current view.
     */
    protected int mViewWidth;

    /**
     * the height of current view.
     */
    protected int mViewHeight;

    /**
     * default Paint.
     */
    protected Paint mDefaultPaint = new Paint();

    /**
     * default TextPaint
     */
    protected TextPaint mDefaultTextPaint = new TextPaint();


    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrentContext = context;
        initData();
    }

    public void initData(){
        Log.i("BaseView","initData");
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("BaseView","onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

}
