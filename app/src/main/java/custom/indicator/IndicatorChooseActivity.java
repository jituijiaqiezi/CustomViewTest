package custom.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lcp.customviewtest.R;


public class IndicatorChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
    }

    public void click(View view) {
        int index;
        switch (view.getId()) {
            case R.id.btn_underline:
                index = 0;
                break;
            case R.id.btn_circle:
                index = 1;
                break;
            default:
                index=0;
                break;
        }
        startActivity(new Intent(this, IndicatorListActivity.class).putExtra("index", index));
    }
}
