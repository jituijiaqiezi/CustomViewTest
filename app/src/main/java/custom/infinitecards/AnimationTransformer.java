package custom.infinitecards;

import android.view.View;

/**
 * Created by linchenpeng on 2017/6/23.
 */

public interface AnimationTransformer {

    String getTag();
    void transformAnimation(View view, float fraction,int cardWidth,int cardHeight,int fromPosition,int toPosition);
    void transformInterpolatedAnimation(View view,float fraction,int cardWidth,int cardHeight,int fromPosition,int toPosition);
}
