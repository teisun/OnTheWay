/** 
 * @Title:  AutoScaleImageView.java 
 * @author:  lee.shenzhou
 * @data:  2016年4月5日 下午8:41:03 <创建时间>
 * 
 * @history：<以下是历史记录>
 *
 * @modifier: <修改人>
 * @modify date: 2016年4月5日 下午8:41:03 <修改时间>
 * @log: <修改内容>
 *
 * @modifier: <修改人>
 * @modify date: 2016年4月5日 下午8:41:03 <修改时间>
 * @log: <修改内容>
 */
package com.road.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/** 
 * 自动缩放的 metro 的 ImageView
 * @author lee.shenzhou 
 * @versionCode 1 <每次修改提交前+1>
 */
public class AutoScaleImageView extends ImageView {
	
	private static final int SCALE_REDUCE_INIT = 0;
	private static final int SCALING = 1;
	private static final int SCALE_ADD_INIT = 6;
	
	/**
	 * 控件的宽
	 */
	private int mWidth;
	
	/**
	 * 控件的高
	 */
	private int mHeight;
	
	/**
	 * 控件的宽1/2
	 */
	private int mCenterWidth;
	
	/**
	 * 控件的高 1/2
	 */
	private int mCenterHeight;
	
	/**
	 * 设置一个缩放的常量
	 */
	private float mMinScale = 0.85f;
	
	/**
	 * 缩放是否结束
	 */
	private boolean isFinish = true;
	
	private Handler mScaleHandler = new Handler() {

		private Matrix matrix = new Matrix();
		private int count = 0;
		private float s;
		
		/**
		 * 是否已经调用了点击事件
		 */
		private boolean isClicked;
		
		@Override
		public void handleMessage(Message msg) {
			
			matrix.set(getImageMatrix());
			
			switch (msg.what) {
			case SCALE_REDUCE_INIT:
				if (!isFinish) {
					mScaleHandler.sendEmptyMessage(SCALE_ADD_INIT);
				
				} else {
					isFinish = false;
					count = 0;
					s = (float) Math.sqrt(Math.sqrt(mMinScale));
					
					beginScale(matrix, s);
					
					mScaleHandler.sendEmptyMessage(SCALING);
					
				}
				
				break;
				
			case SCALING:
				beginScale(matrix, s);
				
				if (count < 4) {
					mScaleHandler.sendEmptyMessage(SCALING);
					
				} else {
					isFinish = true;
					
					if (AutoScaleImageView.this.mOnViewClickListener != null && !isClicked) {
						isClicked = true;
						AutoScaleImageView.this.mOnViewClickListener.onViewClick(AutoScaleImageView.this);
					
					} else {
						isClicked = false;
					}
					
				}
				count++;
				break;
				
			case SCALE_ADD_INIT:
				if(!isFinish) {
					mScaleHandler.sendEmptyMessage(SCALE_ADD_INIT);
					
				} else {
					isFinish = false;
					count = 0;
					s = (float) Math.sqrt(Math.sqrt(1.0f / mMinScale));
					
					beginScale(matrix, s);
					
					mScaleHandler.sendEmptyMessage(SCALING);
				}
				break;

			default:
				break;
			}
			
		}
		
	};
	
	public AutoScaleImageView(Context context) {
		super(context);
	}

	public AutoScaleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	public AutoScaleImageView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		
		if (changed) {
			mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
			mHeight = getHeight() - getPaddingTop() - getPaddingBottom();
			
			mCenterWidth = mWidth / 2;
			mCenterHeight = mHeight / 2;
			
			Drawable drawable = getDrawable();
			BitmapDrawable bd = (BitmapDrawable) drawable;
			// 防止边缘的锯齿
			bd.setAntiAlias(true);
			
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mScaleHandler.sendEmptyMessage(SCALE_REDUCE_INIT);
			break;
			
		case MotionEvent.ACTION_UP:
			mScaleHandler.sendEmptyMessage(SCALE_ADD_INIT);
			break;
		default:
			break;
		}
		
		return true;
	}
	
	protected void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 缩放
	 */
	private synchronized void beginScale(Matrix matrix, float scale) {
		matrix.postScale(scale, scale, mCenterWidth, mCenterHeight);
		setImageMatrix(matrix);
	}
	
	private OnViewClickListener mOnViewClickListener;

	public void setOnClickIntent(OnViewClickListener onViewClickListener) {
		this.mOnViewClickListener = onViewClickListener;
	}
	
	/**
	 * 回调接口
	 */
	public interface OnViewClickListener {
		
		void onViewClick(AutoScaleImageView view);
	
	}
	

}
