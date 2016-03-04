/** 
 * @Title:  ContactsFooterView.java 
 * @author:  zhou.sz
 * @data:  2016年3月4日 上午11:02:48 <创建时间>
 * 
 * @history：<以下是历史记录>
 *
 * @modifier: <修改人>
 * @modify date: 2016年3月4日 上午11:02:48 <修改时间>
 * @log: <修改内容>
 *
 * @modifier: <修改人>
 * @modify date: 2016年3月4日 上午11:02:48 <修改时间>
 * @log: <修改内容>
 */
package com.road.ui.addrbook;

import com.zhou.ontheway.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/** 
 * 通讯录底部列表
 * @author lee.sz 
 * @versionCode 1 <每次修改提交前+1>
 */
public class ContactsFooterView extends RelativeLayout {
	
	private TextView mTxtTotal;
	
	public ContactsFooterView(Context context) {
		super(context);
		initView();
	}
	
	public ContactsFooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	
	public ContactsFooterView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	/**
	 * 初始化 View
	 */
	private void initView() {
		View view = View.inflate(getContext(), R.layout.layout_contacts_footer, this);
		mTxtTotal = (TextView) view.findViewById(R.id.txt_total);
	}
	
	/**
	 * 设置联系人大小
	 * @param total
	 */
	public void setContactTotal(int total) {
		mTxtTotal.setText(total + "位联系人");
	}
	

}
