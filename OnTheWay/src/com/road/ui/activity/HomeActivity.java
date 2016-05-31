package com.road.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.road.ui.addrbook.AddressBookAct;
import com.road.utils.StatusBarCompat;
import com.road.utils.SystemBarTintManager;
import com.road.widget.CustomToast;
import com.zhou.ontheway.R;

public class HomeActivity extends BaseActivity {
	
	private static final String TAG = "HomeActivity";

    private Toolbar mToolbar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // StatusBarCompat.compat(this, getResources().getColor(R.color.green));

//		findViewById(R.id.top_home).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				openActivity(AddressBookAct.class, null);
//			}
//		});
//
//		findViewById(R.id.top_discover).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO
//			}
//		});
		
	}

	private long exitTime = 0;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 按下的如果是BACK，同时没有重复.
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
