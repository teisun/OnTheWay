/** 
 * @Title:  ContactsHeadView.java 
 * @author:  zhou.sz
 * @data:  2016年3月3日 下午11:00:04 <创建时间>
 * 
 * @history：<以下是历史记录>
 *
 * @modifier: <修改人>
 * @modify date: 2016年3月3日 下午11:00:04 <修改时间>
 * @log: <修改内容>
 *
 * @modifier: <修改人>
 * @modify date: 2016年3月3日 下午11:00:04 <修改时间>
 * @log: <修改内容>
 */
package com.road.ui.addrbook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhou.ontheway.R;

/** 
 * 通讯录头部列表
 * @author zhou.sz 
 * @versionCode 1 <每次修改提交前+1>
 */
public class ContactsHeadView extends RelativeLayout {
	
	public ContactsHeadView(Context context) {
		super(context);
		initView();
	}
	
	public ContactsHeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	
	public ContactsHeadView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}
	
	/**
	 * 初始化 View
	 */
	private void initView() {
		View view = View.inflate(getContext(), R.layout.layout_contacts_head, this);
	}
	

}
