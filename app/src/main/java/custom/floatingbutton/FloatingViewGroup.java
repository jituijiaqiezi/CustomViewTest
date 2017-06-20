package custom.floatingbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.lcp.datepickertest.R;

import util.DimentionUtil;

/**
 * Created by linchenpeng on 2017/6/15.
 */

public class FloatingViewGroup extends FrameLayout {
    FloatingView floatingView;
    FloatingButton floatingButton;
    boolean top = true;
    float translateY;
    AnimatorSet animatorSet;
    private static final String TAG = FloatingViewGroup.class.getSimpleName();

    public FloatingViewGroup(Context context) {
        this(context, null);
    }

    public FloatingViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.float_view_group, this);
        floatingView = (FloatingView) findViewById(R.id.float_view);
        floatingButton = (FloatingButton) findViewById(R.id.float_button);
        translateY = DimentionUtil.dp2px(getContext(), 200);

        floatingButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
            }
        });
        floatingView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
            }
        });
    }

    private void clickButton() {
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(floatingButton, "translationY", top ? 0 : translateY, top ? translateY : 0);
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(floatingButton, "rotation", top ? 0 : 45, top ? 45 : 0);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(floatingView, "alpha", top ? 1f : 0.2f, top ? 0.2f : 1f);
        ObjectAnimator backAnimator=ObjectAnimator.ofArgb(floatingView,"backgroundColor", top ? Color.WHITE:Color.BLUE, top ? Color.BLUE:Color.WHITE);
        animatorSet = new AnimatorSet();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (top)
                    floatingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                top = !top;
                if (top)
                    floatingView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.play(translateAnimator).with(rotateAnimator).with(backAnimator);
        animatorSet.setDuration(200);
        animatorSet.start();
    }
}
