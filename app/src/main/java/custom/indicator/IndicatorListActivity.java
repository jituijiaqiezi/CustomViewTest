package custom.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lcp.customviewtest.R;

import java.util.HashMap;
import java.util.Map;

import custom.indicator.sample.SampleLinesStyledTheme;
import custom.indicator.sample.SampleUnderlinesDefault;
import custom.indicator.sample.SampleUnderlinesNoFade;
import custom.indicator.sample.SampleUnderlinesStyledLayout;
import custom.indicator.sample.SampleUnderlinesStyledMethods;
import custom.indicator.sample.SampleUnderlinesStyledTheme;


public class IndicatorListActivity extends AppCompatActivity {

    LinearLayout llayout;
    private Map<String,Class> underlines=new HashMap<String,Class>(){{
        put(SampleUnderlinesDefault.class.getSimpleName(),SampleUnderlinesDefault.class);
        put(SampleUnderlinesNoFade.class.getSimpleName(),SampleUnderlinesNoFade.class);
        put(SampleUnderlinesStyledLayout.class.getSimpleName(),SampleUnderlinesStyledLayout.class);
        put(SampleUnderlinesStyledMethods.class.getSimpleName(),SampleUnderlinesStyledMethods.class);
        put(SampleUnderlinesStyledTheme.class.getSimpleName(), SampleLinesStyledTheme.class);
    }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_list);
        llayout=(LinearLayout)findViewById(R.id.llayout);
        int index=getIntent().getIntExtra("index",0);
        init(index);
    }
    private void init(int index){
        if(index==0){
            for(Map.Entry<String,Class> entry:underlines.entrySet()){
                String key=entry.getKey();
                final Class value=entry.getValue();
                Button button=new Button(this);
                button.setText(key);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(IndicatorListActivity.this,value));
                    }
                });
                llayout.addView(button);
            }
        }
    }
}
