package custom.scroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lcp.customviewtest.R;

public class ScrollerActivity extends AppCompatActivity {

    ScrollerLayout2 scrollerLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        scrollerLayout2 = (ScrollerLayout2) findViewById(R.id.parentView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollerLayout2.smoothScrollTo();
            }
        });
    }
}
