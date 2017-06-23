package custom.infinitecards.transformer;

import custom.infinitecards.CardItem;
import custom.infinitecards.ZIndexTransformer;

/**
 * Created by linchenpeng on 2017/6/23.
 */

public class DefaultZIndexTransformerCommon implements ZIndexTransformer{
    @Override
    public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
        card.zIndex=1f+0.01f*fromPosition+0.01f*(toPosition-fromPosition)*fraction;
    }

    @Override
    public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

    }
}
