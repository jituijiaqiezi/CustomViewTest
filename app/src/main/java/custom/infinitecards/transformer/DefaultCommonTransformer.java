package custom.infinitecards.transformer;

import android.util.Log;
import android.view.View;

import custom.infinitecards.AnimationTransformer;

/**
 * Created by linchenpeng on 2017/6/23.
 */

public class DefaultCommonTransformer implements AnimationTransformer {
    @Override
    public String getTag() {
        return DefaultCommonTransformer.class.getSimpleName();
    }

    @Override
    public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
        Log.i(getTag(), "fromPosition:" + fromPosition + ",toPosition:" + toPosition);
        int positionCount = fromPosition - toPosition;
        float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
        view.setScaleX(scale);
        view.setScaleY(scale);
        view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f * fromPosition - 0.02f * fraction * positionCount));
    }

    @Override
    public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

    }
}
