package custom.viewgroup;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lcp.customviewtest.R;

public class PullToRefreshActivity extends AppCompatActivity {

    private PullToRefreshScrollView pullToRefreshScrollView;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);
        init();
    }
    private void init(){
        pullToRefreshScrollView=(PullToRefreshScrollView)findViewById(R.id.pull_to_refresh_view);
        pullToRefreshScrollView.setPullDownListener(new PullToRefreshScrollView.PullDownListener() {
            @Override
            public void onPullDown() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshScrollView.onPullSuccess();
                        Toast.makeText(PullToRefreshActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                    }
                },3000);
            }
        });
    }
}
