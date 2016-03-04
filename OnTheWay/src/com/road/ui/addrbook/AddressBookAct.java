package com.road.ui.addrbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.road.sortlistview.CharacterParser;
import com.road.sortlistview.PinyinComparator;
import com.road.sortlistview.SideBar;
import com.road.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.road.sortlistview.SortAdapter;
import com.road.sortlistview.SortModel;
import com.road.ui.activity.BaseActivity;
import com.road.utils.LogUtil;
import com.zhou.ontheway.R;

@SuppressLint("DefaultLocale")
public class AddressBookAct extends BaseActivity {

	protected static final String TAG = "AddressBookAct";
	
	private ListView mListView;
	private SideBar mSideBar;
	private TextView mTxtDialog;
	private SortAdapter mAdapter;

	private View parentView;

	/** 汉字转换成拼音的类 */
	private CharacterParser characterParser;
	private List<Object> sourceDateList;

	/** 根据拼音来排列ListView里面的数据类 */
	private PinyinComparator pinyinComparator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = View.inflate(getApplicationContext(), R.layout.activity_address_book, null);
		setContentView(parentView);
		initView();
	}

	/**
	 * 初始化 View
	 */
	private void initView() {
		mListView = (ListView) findViewById(R.id.lv_address_book);
		mSideBar = (SideBar) findViewById(R.id.sidrbar);
		mTxtDialog = (TextView) findViewById(R.id.txt_dialog);
		mSideBar.setTextView(mTxtDialog);

		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sourceDateList = new ArrayList<Object>();
		
		String[] contacts = getResources().getStringArray(R.array.contacts);
		String[] picContacts = getResources().getStringArray(R.array.img_src_contacts);
		
		List<SortModel> sortLists = filledData(contacts, picContacts);
		// 根据a-z进行排序源数据
		Collections.sort(sortLists, pinyinComparator);
		
		Head head = new Head();
		sourceDateList.add(head);
		sourceDateList.addAll(sortLists);
		
//		ContactsHeadView headView = new ContactsHeadView(getApplicationContext());
//		mListView.addHeaderView(headView);
		
		ContactsFooterView footerView = new ContactsFooterView(getApplicationContext());
		footerView.setContactTotal(sourceDateList.size());
		mListView.addFooterView(footerView);
		
		// 设置右侧触摸监听
		mSideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				LogUtil.e(TAG, s);
				if (s.endsWith("↑") || s.endsWith("☆")) {
					mListView.setSelection(0);
				}
				// 该字母首次出现的位置
				int position = mAdapter.getPositionForSection(s.charAt(0));
				LogUtil.e(TAG, "position:" + position);
				if (position != -1) {
					mListView.setSelection(position);
				}

			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				// SortModel sortModel = (SortModel) mAdapter.getItem(position);

			}
		});

		mAdapter = new SortAdapter(getApplicationContext(), sourceDateList);
		mListView.setAdapter(mAdapter);
	}

	@Override
	protected View getApplicationView() {
		return parentView;
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date, String[] imgData) {
		List<SortModel> mSortList = new ArrayList<SortModel>();
		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setImgSrc(imgData[i]);
			sortModel.setName(date[i]);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}


}
