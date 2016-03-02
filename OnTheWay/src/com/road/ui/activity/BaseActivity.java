package com.road.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.road.bean.Info;
import com.road.utils.Configure;
import com.road.utils.LogUtil;
import com.road.utils.ThreadPoolManager;
import com.squareup.picasso.Picasso;

@SuppressLint("NewApi")
public abstract class BaseActivity extends Activity {

	private static final String TAG = "BaseActivity";

	protected ThreadPoolManager mThreadPoolManager;

	protected Context mContext;

	protected ProgressDialog progress;

	@SuppressWarnings("rawtypes")
	protected Info mInfo;

	protected final String INFO_NAME = "info";

	// 状态栏高度
	protected int statusHeight;
	
	// View 绘制的高度
	protected int viewHeight;
	
	// 应用的高度
	protected int applicationHeight;

	// android 版本是否为4.4
	protected boolean isVersionLevel;

	/** 得到全局的View */
	protected abstract View getApplicationView();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4以上
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			isVersionLevel = true;
		} else {
			isVersionLevel = false;
		}

		Configure.init(this);
		mThreadPoolManager = ThreadPoolManager.getInstance();
		mContext = this;

		progress = getProgressDialog("正在加载,请稍后...");
		progress.setCancelable(true);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus && isVersionLevel) {
			getAreaScreen();
			getAreaView();
			getAreaApplication();
			setPaddings(getApplicationView(), 0, statusHeight, 0, (viewHeight - applicationHeight - statusHeight));
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

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
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

	public void dismissProgressDialog() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (progress != null) {
					progress.dismiss();
				}
			}
		});
	}

	public void showProgressDialog() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (progress == null) {
					progress = new ProgressDialog(mContext);
					progress.setMessage("正在加载,请稍后...");
					progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				}
				progress.setCancelable(true);
				try {
					progress.show();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	public void showProgressDialog(final String msg, final boolean isCancel) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				progress = new ProgressDialog(mContext);
				progress.setMessage(msg);
				progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progress.setCancelable(isCancel);
				try {
					progress.show();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public void onDestroy() {
		if (progress != null) {
			progress.dismiss();
		}
		super.onDestroy();
	}

	/** 是否有数据传递过来 */
	@SuppressWarnings("rawtypes")
	public boolean hasInfo() {
		mInfo = (Info) getIntent().getSerializableExtra(INFO_NAME);
		if (mInfo != null) {
			return true;
		}
		return false;
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

	/** 获得配置文件参数 */
	public float getDimens(int id) {
		return getResources().getDimension(id);
	}

	/** 获得配置文件参数 */
	public int getDimensPixelSize(int id) {
		return getResources().getDimensionPixelSize(id);
	}

}
