package com.road.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
	private NavigationView mNavigationView;
	private DrawerLayout mDrawerLayout;

	private HomeFragmentAdapter mHomeFragmentAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);

		initView();
		initToolbar();
		initViewPager();
		initNavigationView();
	}

	private void initView() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mTabLayout = (TabLayout) findViewById(R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mNavigationView = (NavigationView) findViewById(R.id.navigationView);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
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
		mToolbar.setNavigationIcon(R.drawable.icon_menu);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// mToolbar.setOnMenuItemClickListener(this);
	}

	private void initNavigationView() {

		mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

			private MenuItem mPreMenuItem;

			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				if (mPreMenuItem != null) {
					mPreMenuItem.setChecked(false);
				}

				Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

				item.setChecked(true);
				mDrawerLayout.closeDrawers();
				mPreMenuItem = item;
				return true;
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			// 打开抽屉侧滑菜单
			mDrawerLayout.openDrawer(GravityCompat.START);
		}

		return super.onOptionsItemSelected(item);
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
