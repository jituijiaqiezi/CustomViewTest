package custom.path;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.lcp.customviewtest.R;

public class Win8SearchActivity extends AppCompatActivity {

    RelativeLayout rlParent;
    boolean add;
    RadarView radarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win8_search);
        rlParent = (RelativeLayout) findViewById(R.id.rl_parent);
        radarView = new RadarView(this);
        rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add = !add;
                if (add) {
                    rlParent.addView(radarView);
                    radarView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            radarView.getViewTreeObserver().removeOnPreDrawListener(this);
                            radarView.setCenter(50, 80);
                            return false;
                        }
                    });
                } else {
                    rlParent.removeView(radarView);
                }
            }
        });
    }

}
