package custom.path;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.lcp.customviewtest.R;

public class PathActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        imageView=(ImageView)findViewById(R.id.image);

    }
    public void click(View view){
        final float centerX=imageView.getWidth()/2.0f;
        final float centerY=imageView.getHeight()/2.0f;
        Animation animation;
        switch (view.getId()){
            case R.id.btn:
                animation=new Rotate3dAnimation(0,180,centerX,centerY,0f,true);
                break;
            case R.id.btn_new:
                animation=new Rotate3dAnimationNew(PathActivity.this,0,180,centerX,centerY,0f,true);
                break;
            default:
                animation=new Rotate3dAnimationNew(PathActivity.this,0,180,centerX,centerY,0f,true);
                break;
        }
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(animation);
    }

}
