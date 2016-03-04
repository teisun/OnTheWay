package com.road.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.road.ui.addrbook.AddressBookAct;
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
	}

	@Override
	protected View getApplicationView() {
		return parentView;
	}
	

}
