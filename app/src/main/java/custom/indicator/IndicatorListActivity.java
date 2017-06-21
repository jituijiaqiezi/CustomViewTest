package custom.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import custom.indicator.sample.SampleCirclesDefault;
import custom.indicator.sample.SampleCirclesInitialPage;
import custom.indicator.sample.SampleCirclesSnap;
import custom.indicator.sample.SampleCirclesStyledLayout;
import custom.indicator.sample.SampleCirclesStyledMethods;
import custom.indicator.sample.SampleCirclesStyledTheme;
import custom.indicator.sample.SampleUnderlinesDefault;
import custom.indicator.sample.SampleUnderlinesNoFade;
import custom.indicator.sample.SampleUnderlinesStyledLayout;
import custom.indicator.sample.SampleUnderlinesStyledMethods;
import custom.indicator.sample.SampleUnderlinesStyledTheme;


public class IndicatorListActivity extends AppCompatActivity {

    LinearLayout llayout;
    private Map<String, Class> underlines = new HashMap<String, Class>() {{
        put(SampleUnderlinesDefault.class.getSimpleName(), SampleUnderlinesDefault.class);
        put(SampleUnderlinesNoFade.class.getSimpleName(), SampleUnderlinesNoFade.class);
        put(SampleUnderlinesStyledLayout.class.getSimpleName(), SampleUnderlinesStyledLayout.class);
        put(SampleUnderlinesStyledMethods.class.getSimpleName(), SampleUnderlinesStyledMethods.class);
        put(SampleUnderlinesStyledTheme.class.getSimpleName(), SampleUnderlinesStyledTheme.class);
    }};
    private Map<String, Class> circles = new HashMap<String, Class>() {{
        put(SampleCirclesDefault.class.getSimpleName(), SampleCirclesDefault.class);
        put(SampleCirclesInitialPage.class.getSimpleName(), SampleCirclesInitialPage.class);
        put(SampleCirclesSnap.class.getSimpleName(), SampleCirclesSnap.class);
        put(SampleCirclesStyledLayout.class.getSimpleName(), SampleCirclesStyledLayout.class);
        put(SampleCirclesStyledMethods.class.getSimpleName(), SampleCirclesStyledMethods.class);
        put(SampleCirclesStyledTheme.class.getSimpleName(), SampleCirclesStyledTheme.class);
    }};

    private List<Map<String, Class>> list = new ArrayList<Map<String, Class>>() {{
        add(underlines);
        add(circles);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_list);
        llayout = (LinearLayout) findViewById(R.id.llayout);
        int index = getIntent().getIntExtra("index", 0);
        init(index);
    }

    private void init(int index) {
        for (Map.Entry<String, Class> entry : list.get(index).entrySet()) {
            String key = entry.getKey();
            final Class value = entry.getValue();
            Button button = new Button(this);
            button.setText(key);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(IndicatorListActivity.this, value));
                }
            });
            llayout.addView(button);
        }
    }
}
