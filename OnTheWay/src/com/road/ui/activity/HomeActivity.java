package com.road.ui.activity;

import com.zhou.ontheway.R;

import android.os.Bundle;
import android.view.View;

public class HomeActivity extends BaseActivity {

	private View parentView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = View.inflate(mContext, R.layout.activity_home, null);
		setContentView(parentView);

	}

	@Override
	protected View getApplicationView() {
		return parentView;
	}

}
