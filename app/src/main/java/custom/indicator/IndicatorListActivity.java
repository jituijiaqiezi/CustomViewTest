package custom.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lcp.customviewtest.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import custom.indicator.sample.SampleCirclesDefault;
import custom.indicator.sample.SampleCirclesInitialPage;
import custom.indicator.sample.SampleCirclesSnap;
import custom.indicator.sample.SampleCirclesStyledLayout;
import custom.indicator.sample.SampleCirclesStyledMethods;
import custom.indicator.sample.SampleCirclesStyledTheme;
import custom.indicator.sample.SampleLinesDefault;
import custom.indicator.sample.SampleLinesStyledLayout;
import custom.indicator.sample.SampleLinesStyledMethods;
import custom.indicator.sample.SampleLinesStyledTheme;
import custom.indicator.sample.SampleTabsDefault;
import custom.indicator.sample.SampleTabsStyled;
import custom.indicator.sample.SampleTabsWithIcons;
import custom.indicator.sample.SampleTitlesBottom;
import custom.indicator.sample.SampleTitlesCenterClickListener;
import custom.indicator.sample.SampleTitlesDefault;
import custom.indicator.sample.SampleTitlesInitialPage;
import custom.indicator.sample.SampleTitlesStyledLayout;
import custom.indicator.sample.SampleTitlesStyledMethods;
import custom.indicator.sample.SampleTitlesStyledTheme;
import custom.indicator.sample.SampleTitlesTriangle;
import custom.indicator.sample.SampleTitlesWithListener;
import custom.indicator.sample.SampleUnderlinesDefault;
import custom.indicator.sample.SampleUnderlinesNoFade;
import custom.indicator.sample.SampleUnderlinesStyledLayout;
import custom.indicator.sample.SampleUnderlinesStyledMethods;
import custom.indicator.sample.SampleUnderlinesStyledTheme;


public class IndicatorListActivity extends AppCompatActivity {

    LinearLayout llayout;
    private List<Class> underlines = new LinkedList<Class>() {{
        add(SampleUnderlinesDefault.class);
        add(SampleUnderlinesNoFade.class);
        add(SampleUnderlinesStyledLayout.class);
        add(SampleUnderlinesStyledMethods.class);
        add(SampleUnderlinesStyledTheme.class);
    }};
    private List<Class> circles = new LinkedList<Class>() {{
        add(SampleCirclesDefault.class);
        add(SampleCirclesInitialPage.class);
        add(SampleCirclesSnap.class);
        add(SampleCirclesStyledLayout.class);
        add(SampleCirclesStyledMethods.class);
        add(SampleCirclesStyledTheme.class);
    }};

    private List<Class> lines = new LinkedList<Class>() {{
        add(SampleLinesDefault.class);
        add(SampleLinesStyledLayout.class);
        add(SampleLinesStyledMethods.class);
        add(SampleLinesStyledTheme.class);
    }};

    private List<Class> tabs = new LinkedList<Class>() {{
        add(SampleTabsDefault.class);
        add(SampleTabsStyled.class);
        add(SampleTabsWithIcons.class);
    }};

    private List<Class> titles=new LinkedList<Class>(){{
        add(SampleTitlesDefault.class);
        add(SampleTitlesStyledLayout.class);
        add(SampleTitlesStyledMethods.class);
        add(SampleTitlesStyledTheme.class);
        add(SampleTitlesBottom.class);
        add(SampleTitlesCenterClickListener.class);
        add(SampleTitlesInitialPage.class);
        add(SampleTitlesTriangle.class);
        add(SampleTitlesWithListener.class);
    }};

    private List<List<Class>> list = new ArrayList<List<Class>>() {{
        add(underlines);
        add(circles);
        add(lines);
        add(tabs);
        add(titles);
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
        for (final Class value : list.get(index)) {
            Button button = new Button(this);
            button.setText(value.getSimpleName());
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
