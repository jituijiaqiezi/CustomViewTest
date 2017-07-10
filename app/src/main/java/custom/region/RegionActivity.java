package custom.region;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lcp.customviewtest.R;

public class RegionActivity extends AppCompatActivity {

    RemoteControlView remoteControlView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        remoteControlView=(RemoteControlView)findViewById(R.id.remote_control_view);
        remoteControlView.setListener(new RemoteControlView.MenuListener() {
            @Override
            public void onCenterCliched() {
                showToast("点击中间");
            }

            @Override
            public void onUpCliched() {
                showToast("点击上面");
            }

            @Override
            public void onRightCliched() {
                showToast("点击右边");
            }

            @Override
            public void onDownCliched() {
                showToast("点击下边");
            }

            @Override
            public void onLeftCliched() {
                showToast("点击左边");
            }
        });
    }
    private void showToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
