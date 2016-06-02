package com.road.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.road.bean.Info;
import com.road.utils.LogUtil;
import com.road.utils.SystemBarTintManager;
import com.road.utils.ThreadPoolManager;
import com.squareup.picasso.Picasso;
import com.zhou.ontheway.R;

public abstract class BaseActivity extends AppCompatActivity {

	private static final String TAG = "BaseActivity";

	protected Context mContext;

	protected Info mInfo;

	protected final String INFO_NAME = "info";

	// 状态栏高度
	protected int statusHeight;
	// View 绘制的高度
	protected int viewHeight;
	// 应用的高度
	protected int applicationHeight;

	protected ThreadPoolManager mThreadPoolManager;

	private int statusTinRes = -1;
	private boolean isSetStatusBar;
	private SystemBarTintManager tintManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isSetStatusBar) { // 4.4以上
			// 透明状态栏
			 getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

			tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			if (statusTinRes != -1) {
				tintManager.setStatusBarTintResource(statusTinRes);
			} else {
				tintManager.setStatusBarTintResource(R.color.material_primary_dark);
			}
		}

		mContext = this;
		mThreadPoolManager = ThreadPoolManager.getInstance();
	}

	/**
	 * 是否设置沉浸式
	 */
	public void setSetStatusBar(boolean isSetStatusBar) {
		this.isSetStatusBar = isSetStatusBar;
	}

	/**
	 * 设置沉浸栏的颜色
	 */
	public void setStatusBarTintRes(int res) {
		statusTinRes = res;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			// getAreaScreen();
			// getAreaView();
			// getAreaApplication();
			// setPaddings(getApplicationView(), 0, statusHeight, 0, (viewHeight - applicationHeight - statusHeight));
		}
	}

	/** 设置View的Margin */
	protected void setMargins(View v, int left, int top, int right, int bottom) {
		if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
			p.setMargins(left, top, right, bottom);
			v.requestLayout();
		}
	}

	/** 设置View的Padding */
	protected void setPaddings(View v, int left, int top, int right, int bottom) {
		v.setPadding(left, top, right, bottom);
		v.requestLayout();
	}
	
	/** 得到屏幕大小 */
	protected void getAreaScreen() {
		Display display = getWindowManager().getDefaultDisplay();
		Point outP = new Point();
		display.getSize(outP);
		LogUtil.d(TAG, "AreaScreen:" + " width=" + outP.x + " height=" + outP.y);
	}

	/** 得到应用的大小 */
	protected void getAreaApplication() {
		Rect outRect = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
		statusHeight = outRect.top;
		applicationHeight = outRect.height();
		LogUtil.d(TAG, "AreaApplication:" + " width=" + outRect.width()
				+ " height=" + outRect.height() + " top=" + outRect.top 
				+ " bottom=" + outRect.bottom);
	}

	/** 得到View绘制的大小 */
	protected void getAreaView() {
		// 用户绘制区域
		Rect outRect = new Rect();
		getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
		viewHeight = outRect.height();
		LogUtil.d(TAG, "AreaView:" + " width=" + outRect.width() + " height="
				+ outRect.height());
	}

	public ProgressDialog getProgressDialog(String msg) {
		ProgressDialog progressDialog = new ProgressDialog(mContext);
		progressDialog.setMessage(msg);
		progressDialog.setCancelable(true);
		return progressDialog;
	}

	/**
	 * 运行在主线程中的Toast
	 */
	public void showToastOnUI(final String msg) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	/** 弹出Toast */
	public void showToast(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	/** 子线程执行一个任务 */
	public void executeTask(Runnable run) {
		mThreadPoolManager.executeTask(run);
	}

	/** 隐藏输入法 */
	public void hideSoftInput(Context context, View achor) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(achor.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/** 是否有数据传递过来 */
	public boolean hasInfo() {
		mInfo = (Info) getIntent().getSerializableExtra(INFO_NAME);
		return mInfo != null;
	}

	/**
	 * 打开一个Activity
	 * 
	 * @param clazz
	 * @param info
	 */
	@SuppressWarnings("rawtypes")
	public void openActivity(final Class clazz, final Info info) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent();
				intent.setClass(mContext, clazz);
				if (info != null) {
					intent.putExtra(INFO_NAME, info);
				}
				startActivity(intent);
			}
		});
	}

	/** 加载图片 */
	public void loadIMG(ImageView img, String url, int placeholder, int errorId) {
		Picasso.with(mContext).load(url).error(errorId)
				.placeholder(placeholder).into(img);
	}

	/** 加载图片 */
	public void loadIMG(ImageView img, int id) {
		Picasso.with(mContext).load(id).into(img);
	}


}