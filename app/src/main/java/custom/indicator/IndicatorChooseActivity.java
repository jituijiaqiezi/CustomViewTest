package custom.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lcp.customviewtest.R;

import java.util.LinkedList;
import java.util.List;


public class IndicatorChooseActivity extends AppCompatActivity {

    LinearLayout llayout;
    List<String> indicators = new LinkedList<String>() {{
        add("UnderlinePageIndicator");
        add("CirclePageIndicator");
        add("LinePageIndicator");
        add("TabPageIndicator");
        add("TitleIndicator");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        init();
    }

    private void init() {
        llayout = (LinearLayout) findViewById(R.id.llayout);
        for (int i = 0; i < indicators.size(); i++) {
            Button button = new Button(this);
            button.setText(indicators.get(i));
            final int index = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(IndicatorChooseActivity.this, IndicatorListActivity.class).putExtra("index", index));
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(100, 50, 100, 50);
            llayout.addView(button, params);
        }
    }

}
