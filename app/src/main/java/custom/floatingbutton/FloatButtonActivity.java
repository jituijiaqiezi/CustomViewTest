package custom.floatingbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lcp.datepickertest.R;

public class FloatButtonActivity extends AppCompatActivity {

    FloatingViewGroup floatingViewGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_button);
        init();
    }
    private void init(){}
}
