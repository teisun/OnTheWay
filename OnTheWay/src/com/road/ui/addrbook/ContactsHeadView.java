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
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhou.ontheway.R;

/** 
 * 通讯录头部列表
 * @author lee.sz 
 * @versionCode 1 <每次修改提交前+1>
 */
public class ContactsHeadView extends RelativeLayout implements OnClickListener {
	
	private TextView mTxtMessage;
	
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
		view.findViewById(R.id.rela_newfriends).setOnClickListener(this);
		view.findViewById(R.id.rela_chatroom).setOnClickListener(this);
		view.findViewById(R.id.rela_mark).setOnClickListener(this);
		view.findViewById(R.id.rela_publicno).setOnClickListener(this);
		mTxtMessage = (TextView) view.findViewById(R.id.txt_message);
	}
	
	/**
	 * 设置消息记录大小
	 * @param count
	 */
	public void setMessageCount(int count) {
		mTxtMessage.setVisibility(View.VISIBLE);
		mTxtMessage.setText(count + "");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rela_newfriends:

			break;

		case R.id.rela_chatroom:

			break;

		case R.id.rela_mark:

			break;

		case R.id.rela_publicno:

			break;

		default:
			break;
		}
	}
	

}
