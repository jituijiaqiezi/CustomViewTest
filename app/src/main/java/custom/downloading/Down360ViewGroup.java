package custom.downloading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lcp.customviewtest.R;

/**
 * Created by linchenpeng on 2017/6/14.
 */

public class Down360ViewGroup extends RelativeLayout{
    private Down360LoadingView down360LoadingView;
    private ImageView cancelImg;
    private ImageView stopContinueImg;
    private Drawable cancelBack;
    private Drawable continueBack;
    private Drawable stopBack;
    private int statusSize;
    private int statusColor;

    private int loadPointColor;
    private int bgColor;
    private int progressColor;
    private int collectSpeed;
    private int collectRotateSpeed;
    private int expandSpeed;
    private int rightLoadingSpeed;
    private int leftLoadingSpeed;

    public Down360ViewGroup(Context context){
        this(context,null);
    }
    public Down360ViewGroup(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public Down360ViewGroup(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        initArgs(context,attrs);
        View.inflate(context,R.layout.down_viewgroup_layout,this);
        down360LoadingView=(Down360LoadingView)findViewById(R.id.down_load);
        Down360LoadingView.ArgParams argParams=new Down360LoadingView.ArgParams();
        argParams.statusSize=statusSize;
        argParams.statusColor=statusColor;
        argParams.loadPointColor=loadPointColor;
        argParams.bgColor = bgColor;
        argParams.progressColor = progressColor;
        argParams.shrinkSpeed = collectSpeed;
        argParams.shrinkRotateSpeed = collectRotateSpeed;
        argParams.expandSpeed = expandSpeed;
        argParams.rightLoadingSpeed = rightLoadingSpeed;
        argParams.leftLoadingSpeed = leftLoadingSpeed;
        down360LoadingView.setArgs(argParams);

        cancelImg=(ImageView)findViewById(R.id.cancel_img);
        stopContinueImg=(ImageView)findViewById(R.id.stop_img);
        cancelImg.setImageDrawable(cancelBack);
        stopContinueImg.setImageDrawable(continueBack);
        cancelImg.setVisibility(View.GONE);
        stopContinueImg.setVisibility(View.GONE);
        cancelImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                down360LoadingView.setCancel();
                cancelImg.setVisibility(View.GONE);
                stopContinueImg.setVisibility(View.GONE);
            }
        });
        stopContinueImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStop()){
                    down360LoadingView.setStop(false);
                    stopContinueImg.setImageDrawable(continueBack);
                }else{
                    down360LoadingView.setStop(true);
                    stopContinueImg.setImageDrawable(stopBack);
                }
            }
        });
    }

    private void initArgs(Context context,AttributeSet attrs){
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.Down360LoadingView);
        try{
            cancelBack=array.getDrawable(R.styleable.Down360LoadingView_cancel_back_icon);
            if(cancelBack==null)
                cancelBack=getResources().getDrawable(R.drawable.close,null);
            continueBack=array.getDrawable(R.styleable.Down360LoadingView_continue_back_icon);
            if(continueBack==null)
                continueBack=getResources().getDrawable(R.drawable.play,null);
            stopBack=array.getDrawable(R.styleable.Down360LoadingView_stop_back_icon);
            if(stopBack==null)
                stopBack=getResources().getDrawable(R.drawable.stop,null);
            if(cancelBack==null||continueBack==null||stopBack==null)
                throw new IllegalArgumentException("图标为空");

            statusSize=(int)array.getDimension(R.styleable.Down360LoadingView_status_text_size,sp2px(15));
            statusColor=array.getColor(R.styleable.Down360LoadingView_status_text_color, Color.WHITE);
            loadPointColor=array.getColor(R.styleable.Down360LoadingView_load_point_color,Color.WHITE);
            bgColor=array.getColor(R.styleable.Down360LoadingView_bg_color,Color.parseColor("#00CC99"));
            progressColor=array.getColor(R.styleable.Down360LoadingView_progress_color,Color.parseColor("#4400CC99"));
            collectSpeed=array.getInt(R.styleable.Down360LoadingView_collect_speed,800);
            collectRotateSpeed=array.getInt(R.styleable.Down360LoadingView_collect_rotate_speed,1500);
            expandSpeed=array.getInt(R.styleable.Down360LoadingView_expand_speed,1000);
            rightLoadingSpeed=array.getInt(R.styleable.Down360LoadingView_right_loading_speed,7);
            leftLoadingSpeed=array.getInt(R.styleable.Down360LoadingView_left_loading_speed,2000);
        }finally {
            array.recycle();
        }
    }

    public boolean isStop(){
        return down360LoadingView.isStop();
    }
    public void setProgress(int progress){
        down360LoadingView.setProgress(progress);
        if(progress>0){
            if(stopContinueImg.getVisibility()==View.GONE&&cancelImg.getVisibility()==View.GONE){
                stopContinueImg.setVisibility(View.VISIBLE);
                cancelImg.setVisibility(View.VISIBLE);
            }
        }
    }
    public void setOnProgressStateChangeListener(Down360LoadingView.OnProgressStateChangeListener onProgressStateChangeListener){
        down360LoadingView.setOnProgressStateChangeListener(onProgressStateChangeListener);
    }

    public Down360LoadingView.Status getStatus(){
        return down360LoadingView.getStatus();
    }

    private int sp2px(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,value,getResources().getDisplayMetrics());
    }
}
