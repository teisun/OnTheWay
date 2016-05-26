/** 
 * @Title:  WellcomeFragment.java 
 * @author:  lee.shenzhou
 * @data:  2016年5月26日 下午8:51:25 <创建时间>
 * 
 * @history：<以下是历史记录>
 *
 * @modifier: <修改人>
 * @modify date: 2016年5月26日 下午8:51:25 <修改时间>
 * @log: <修改内容>
 *
 * @modifier: <修改人>
 * @modify date: 2016年5月26日 下午8:51:25 <修改时间>
 * @log: <修改内容>
 */
package com.road.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.road.ui.activity.HomeActivity;
import com.zhou.ontheway.R;

/** 
 * TODO<请描述这个类是干什么的> 
 * @author lee.shenzhou 
 * @versionCode 1 <每次修改提交前+1>
 */
public class WellcomeFragment extends Fragment {
	
	private static final String KEY_CONTENT = "WellcomeFragment_Content";
	private static final String KEY_IS_LAST_PIC = "WellcomeFragment_Is_Last_Pic";
	
	private int mContent;
	private boolean mIsLastPic;
	
	public static WellcomeFragment newInstance(int content, boolean isLastPic) {
		WellcomeFragment fragment = new WellcomeFragment();
		fragment.mContent = content;
		fragment.mIsLastPic = isLastPic;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			mContent = savedInstanceState.getInt(KEY_CONTENT);
			mIsLastPic = savedInstanceState.getBoolean(KEY_IS_LAST_PIC);
		}
		
		View root = inflater.inflate(R.layout.fragment_viewpager, container, false);
		ImageView iv = (ImageView) root.findViewById(R.id.img_well);
		iv.setImageResource(mContent);
		Button btn = (Button) root.findViewById(R.id.btn_start);
		if (mIsLastPic) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.GONE);
		}
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), HomeActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		return root;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_CONTENT, mContent);
		outState.putBoolean(KEY_IS_LAST_PIC, mIsLastPic);
	}
	
}
