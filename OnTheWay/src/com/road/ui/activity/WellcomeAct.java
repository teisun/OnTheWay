/** 
 * @Title:  WellcomeAct.java 
 * @author:  lee.shenzhou
 * @data:  2016年5月26日 下午8:43:55 <创建时间>
 * 
 * @history：<以下是历史记录>
 *
 * @modifier: <修改人>
 * @modify date: 2016年5月26日 下午8:43:55 <修改时间>
 * @log: <修改内容>
 *
 * @modifier: <修改人>
 * @modify date: 2016年5月26日 下午8:43:55 <修改时间>
 * @log: <修改内容>
 */
package com.road.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.road.ui.adapter.WellcomeFragmentAdapter;
import com.road.ui.component.DirectionalViewPager;
import com.zhou.ontheway.R;

/**
 * TODO<请描述这个类是干什么的>
 * 
 * @author lee.shenzhou
 * @versionCode 1 <每次修改提交前+1>
 */
public class WellcomeAct extends FragmentActivity {

	private DirectionalViewPager mDirectionalViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wellcome);

		// Set up the pager
		mDirectionalViewPager = (DirectionalViewPager) findViewById(R.id.viewpager);
		mDirectionalViewPager.setAdapter(new WellcomeFragmentAdapter(
				getSupportFragmentManager()));
		mDirectionalViewPager.setOrientation(DirectionalViewPager.VERTICAL);
	}

}
