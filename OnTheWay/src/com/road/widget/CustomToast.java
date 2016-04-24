/** 
 * @Title:  CustomToast.java 
 * @author:  lee.shenzhou
 * @data:  2016年4月8日 下午4:57:52 <创建时间>
 * 
 * @history：<以下是历史记录>
 *
 * @modifier: <修改人>
 * @modify date: 2016年4月8日 下午4:57:52 <修改时间>
 * @log: <修改内容>
 *
 * @modifier: <修改人>
 * @modify date: 2016年4月8日 下午4:57:52 <修改时间>
 * @log: <修改内容>
 */
package com.road.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhou.ontheway.R;

/**
 * 自定义Toast
 * 
 * @author lee.shenzhou
 * @versionCode 1 <每次修改提交前+1>
 */
public class CustomToast {

	public static void showToast(Context context, ViewGroup root,
			CharSequence text, int duration) {
		View layout = LayoutInflater.from(context).inflate(R.layout.toast_home,
				root);
		TextView message = (TextView) layout.findViewById(R.id.txt_message);
		message.setText(text);

		Toast toast = new Toast(context);
		toast.setDuration(duration);
		toast.setGravity(Gravity.TOP, 0, 0);  
		toast.setView(layout);
		toast.show();
	}

}
