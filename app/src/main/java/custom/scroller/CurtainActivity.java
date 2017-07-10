package custom.scroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.lcp.customviewtest.R;

public class CurtainActivity extends Activity {
	private LoginView mLoginView;
	private View view_mask;
	private ImageView btn_login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_login_main);
		initView();
	}

	private void initView() {
		mLoginView = (LoginView)findViewById(R.id.mLoginView);
		view_mask = (View)findViewById(R.id.view_mask);
		btn_login = (ImageView)findViewById(R.id.btn_login);
		mLoginView.setEnabled(true);
		//设置遮罩阴影层点击消失该界面
		view_mask.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mLoginView.isShow()){
					mLoginView.dismiss();
				}
			}
		});
		//设置登录界面状态监听
		mLoginView.setOnStatusListener(new LoginView.onStatusListener() {

			@Override
			public void onShow() {
				//显示
				view_mask.setVisibility(View.VISIBLE);
			}

			@Override
			public void onDismiss() {
				//隐藏
				view_mask.setVisibility(View.GONE);
			}
		});
		btn_login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mLoginView.isShow()){
					mLoginView.dismiss();
				}else{
					mLoginView.show();
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode ==  KeyEvent.KEYCODE_BACK){
			if(mLoginView.isShow()){
				mLoginView.dismiss();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



}
