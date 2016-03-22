/** 
 * @Title:  LoadingDialog.java 
 * @author:  zhou.ni
 * @data:  2016年3月22日 下午11:35:57 <创建时间>
 * 
 * @history：<以下是历史记录>
 *
 * @modifier: <修改人>
 * @modify date: 2016年3月22日 下午11:35:57 <修改时间>
 * @log: <修改内容>
 *
 * @modifier: <修改人>
 * @modify date: 2016年3月22日 下午11:35:57 <修改时间>
 * @log: <修改内容>
 */
package com.road.ui.component;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;

import com.zhou.ontheway.R;

/** 
 * TODO<请描述这个类是干什么的> 
 * @author zhou.ni 
 * @versionCode 1 <每次修改提交前+1>
 */
public class LoadingDialog extends Dialog {
	
	private TextView mTxtMessage;
	
	public LoadingDialog(Context context, int theme) {
		super(context, R.style.CustomProgressDialog);
		initView();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		setContentView(R.layout.dialog_loading);
		// 居中
		getWindow().getAttributes().gravity = Gravity.CENTER; 
		// 点击空白不取消
		setCanceledOnTouchOutside(false); 
		// 点击返回按钮不取消
		setCancelable(false); 
	}

	/**
	 * @param mTxtMessage the mTxtMessage to set
	 */
	public void setMessage(String message) {
		if (!TextUtils.isEmpty(message)) {
			mTxtMessage.setText(message);
		}
	}
	
	

}
