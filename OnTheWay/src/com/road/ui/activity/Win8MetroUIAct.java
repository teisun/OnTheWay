/** 
 * @Title:  Win8MetroUIAct.java 
 * @author:  lee.shenzhou
 * @data:  2016年4月5日 下午9:13:14 <创建时间>
 * 
 * @history：<以下是历史记录>
 *
 * @modifier: <修改人>
 * @modify date: 2016年4月5日 下午9:13:14 <修改时间>
 * @log: <修改内容>
 *
 * @modifier: <修改人>
 * @modify date: 2016年4月5日 下午9:13:14 <修改时间>
 * @log: <修改内容>
 */
package com.road.ui.activity;

import com.zhou.ontheway.R;

import android.os.Bundle;
import android.view.View;

/** 
 * TODO<请描述这个类是干什么的> 
 * @author lee.shenzhou 
 * @versionCode 1 <每次修改提交前+1>
 */
public class Win8MetroUIAct extends BaseActivity {
	
	private View parentView;
	
	@Override
	protected View getApplicationView() {
		return parentView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = View.inflate(mContext, R.layout.activity_win8_metro, null);
		setContentView(parentView);
	}
	

}
