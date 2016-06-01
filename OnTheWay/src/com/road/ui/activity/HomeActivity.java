package com.road.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.road.ui.adapter.HomeFragmentAdapter;
import com.road.widget.CustomToast;
import com.zhou.ontheway.R;

import java.util.Arrays;

public class HomeActivity extends BaseActivity {

	private static final String TAG = "HomeActivity";

	private Toolbar mToolbar;
	private TabLayout mTabLayout;
	private ViewPager mViewPager;

	private HomeFragmentAdapter mHomeFragmentAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);

		initView();
		initToolbar();
		initViewPager();
	}

	private void initView() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mTabLayout = (TabLayout) findViewById(R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
	}

	private void initViewPager() {
		String[] titles = {"aa", "vv", "ss"};
		mHomeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), Arrays.asList(titles));
		mViewPager.setAdapter(mHomeFragmentAdapter);
		mTabLayout.setupWithViewPager(mViewPager); // 将TabLayout和ViewPager关联起来。
		mTabLayout.setTabsFromPagerAdapter(mHomeFragmentAdapter); // 给Tabs设置适配器
	}

	private void initToolbar() {
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		mToolbar.setTitle("greek lee");
		mToolbar.setSubtitle("zhou");
		mToolbar.setLogo(R.drawable.ic_launcher);
		mToolbar.setNavigationIcon(R.drawable.icon_search);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// mToolbar.setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_home, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 按下的如果是BACK，同时没有重复.
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				CustomToast.showToast(getApplicationContext(), null, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
			}
		}
		return true;
	}


}
