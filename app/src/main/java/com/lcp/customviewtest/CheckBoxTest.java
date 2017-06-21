package com.lcp.customviewtest;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;

public class CheckBoxTest extends AppCompatActivity {

    CheckBox checkBox;
    Button btn;
    Animation showAnimation, hideAnimation;
    Animator showAnimator, hideAnimator;
    boolean show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_test);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        btn = (Button) findViewById(R.id.btn);
        show = checkBox.getVisibility() != View.GONE;
        btn.setText(show ? "隐藏" : "显示");
        init();
    }

    private void init() {

        showAnimation = new ScaleAnimation(0, 1, 0, 1, checkBox.getPivotX(), checkBox.getPivotY());
        showAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                btn.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                show = true;
                btn.setText("隐藏");
                btn.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hideAnimation = new ScaleAnimation(1, 0, 1, 0);
        hideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                btn.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                show = false;
                btn.setText("显示");
                btn.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        showAnimation.setDuration(2000);
        showAnimation.setFillAfter(true);
        hideAnimation.setDuration(2000);
        hideAnimation.setFillAfter(true);
    }

    public void click(View view) {
        /*boolean gone = checkBox.getVisibility() == View.GONE;
        btn.setText(gone ? "隐藏" : "显示");*/
        if (!show) {
            checkBox.startAnimation(showAnimation);
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.startAnimation(hideAnimation);
            checkBox.setVisibility(View.GONE);
        }
    }
}
