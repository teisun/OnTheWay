/** 
 * @Title:  SideBar.java 
 * @author:  zhou.sz
 * @data:  2016年3月2日 下午7:41:06 <创建时间>
 * 
 * @history：<以下是历史记录>
 *
 * @modifier: <修改人>
 * @modify date: 2016年3月2日 下午7:41:06 <修改时间>
 * @log: <修改内容>
 *
 * @modifier: <修改人>
 * @modify date: 2016年3月2日 下午7:41:06 <修改时间>
 * @log: <修改内容>
 */
package com.road.sortlistview;

import com.zhou.ontheway.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/** 
 * ListView 右侧的字母索引 View
 * @author zhou.sz 
 * @versionCode 1 <每次修改提交前+1>
 */
public class SideBar extends View {
	
	// 26个字母  
    public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",  
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
            "W", "X", "Y", "Z", "#" };  
	
    private int choose = -1; // 选中
    
    private Paint mPaint = new Paint();
    private TextView mTextDialog;
    
    // 触摸事件  
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;  
    
    /** 
     * 向外公开的方法 
     * @param onTouchingLetterChangedListener 
     */  
    public void setOnTouchingLetterChangedListener(  
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {  
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;  
    } 
    
    /** 
     * 为SideBar设置显示字母的TextView 
     * @param mTextDialog 
     */  
    public void setTextView(TextView mTextDialog) {  
        this.mTextDialog = mTextDialog;  
    }  
    
	public SideBar(Context context) {
		super(context);
	}
	
	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获得焦点改变的背景色
		int height = getHeight(); // 获取对应高度
		int width = getWidth(); // 获取对应宽度
		int singleHeight = height / b.length; // 获取每一个字母的高度
		
		for (int i = 0; i < b.length; i++) {
			mPaint.setColor(Color.parseColor("#565656"));
			mPaint.setTypeface(Typeface.DEFAULT);
			mPaint.setAntiAlias(true);
			mPaint.setTextSize(26);
			// 选中的状态  
			if (choose == i) {
				mPaint.setColor(Color.parseColor("#ffffff"));
				mPaint.setFakeBoldText(true);
			}
			// x坐标等于中间 - 字符串宽度的一半.
			float xPos = width / 2 - mPaint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, mPaint);
			mPaint.reset();// 重置画笔
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY(); // 点击 y 坐标
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
		
		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000));
			choose = -1;
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;
			
		default:
			setBackgroundResource(R.drawable.sidebar_bg);
			if (oldChoose != c) {
				if (c >= 0 && c < b.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(b[c]);
					}
					if (mTextDialog != null) {
						mTextDialog.setText(b[c]);
						mTextDialog.setVisibility(View.VISIBLE);
					}
					choose = c;
					invalidate();
				}
			}
			break;
		}
		return true;
	}
	
	
	/**
	 * 触摸事件 的接口
	 * @author zhou.sz 
	 */
    public interface OnTouchingLetterChangedListener {  
    	
        void onTouchingLetterChanged(String s);
        
    } 

}
