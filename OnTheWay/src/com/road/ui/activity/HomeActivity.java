package com.road.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.road.ui.addrbook.AddressBookAct;
import com.road.widget.CustomToast;
import com.zhou.ontheway.R;

public class HomeActivity extends BaseActivity {
	
	public static final String TAG = "HomeActivity";
	
	private View parentView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = View.inflate(mContext, R.layout.activity_home, null);
		setContentView(parentView);
		
		findViewById(R.id.top_home).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openActivity(AddressBookAct.class, null);
			}
		});
		
		findViewById(R.id.top_discover).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO
			}
		});
		
	}

	@Override
	protected View getApplicationView() {
		return parentView;
	}
	
	private long exitTime = 0;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 按下的如果是BACK，同时没有重复。为解决 V5 从欢迎界面退出偶现黑屏 bug
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				CustomToast.showToast(getApplicationContext(), (ViewGroup) findViewById(R.id.root), "再按一次退出程序", Toast.LENGTH_SHORT);
				exitTime = System.currentTimeMillis();
			} else {
				finish();
			}
		}
		return true;
	}

}
