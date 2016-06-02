/** 
 * @Title:  WellComeFragmentAdapter.java
 * @author:  lee.shenzhou
 * @data:  2016年5月26日 下午9:07:37 <创建时间>
 * 
 * @history：<以下是历史记录>
 *
 * @modifier: <修改人>
 * @modify date: 2016年5月26日 下午9:07:37 <修改时间>
 * @log: <修改内容>
 *
 * @modifier: <修改人>
 * @modify date: 2016年5月26日 下午9:07:37 <修改时间>
 * @log: <修改内容>
 */
package com.road.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.road.ui.fragment.WellcomeFragment;
import com.zhou.ontheway.R;

/**
 * TODO<请描述这个类是干什么的>
 * 
 * @author lee.shenzhou
 * @versionCode 1 <每次修改提交前+1>
 */
public class WellComeFragmentAdapter extends FragmentPagerAdapter {

	private static final int[] CONTENT = new int[] { R.drawable.wellcome_1,
			R.drawable.wellcome_2, R.drawable.wellcome_3, R.drawable.wellcome_4 };

	public WellComeFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		boolean isLastPic = false;
		if (position == CONTENT.length - 1) {
			isLastPic = true;
		}
		return WellcomeFragment.newInstance(CONTENT[position], isLastPic);
	}

	@Override
	public int getCount() {
		return CONTENT.length;
	}

}
