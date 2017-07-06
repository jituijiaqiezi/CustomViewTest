package custom.scroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.lcp.customviewtest.R;

public class CurtainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.curtain_activity);
		initView();
	}

	private void initView() {
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
