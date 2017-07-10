package custom.viewgroup;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lcp.customviewtest.R;

/**
 * Created by linchenpeng on 2017/7/7.
 */

public class FooterLayout extends FrameLayout {
    private TextView textPull, textPullRadar;

    public FooterLayout(@NonNull Context context) {
        super(context);
    }

    public FooterLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FooterLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textPull = (TextView) findViewById(R.id.text_pull);
        textPullRadar = (TextView) findViewById(R.id.text_pull_radar);
    }

    public void showFooterPull() {
        textPull.setVisibility(View.VISIBLE);
        textPullRadar.setVisibility(View.INVISIBLE);
    }

    public void showFooterRadar() {
        textPull.setVisibility(View.INVISIBLE);
        textPullRadar.setVisibility(View.VISIBLE);
    }

    public void hideFooter() {
        textPull.setVisibility(View.INVISIBLE);
        textPullRadar.setVisibility(View.INVISIBLE);
    }
}
