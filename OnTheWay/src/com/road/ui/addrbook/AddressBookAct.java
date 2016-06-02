package com.road.ui.addrbook;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;
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
import com.road.ui.component.LoadingDialog;
import com.road.utils.LogUtil;
import com.zhou.ontheway.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AddressBookAct extends BaseActivity {

	private static final String TAG = "AddressBookAct";

	private ListView mListView;
	private SideBar mSideBar;
	private TextView mTxtDialog;
	private SortAdapter mAdapter;

	// 角标
	private ContactsFooterView footerView;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	// 数据源
	private List<Object> sourceDateList;
	// 联系人列表
	private List<SortModel> contactLists;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_address_book);

		initView();
		getPhoneContacts();
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

		// 添加角标
		footerView = new ContactsFooterView(getApplicationContext());
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

	/**
	 * 得到手机通讯录联系人信息
	 **/
	private void getPhoneContacts() {

		final LoadingDialog dialog = new LoadingDialog(this);
		dialog.show();

		executeTask(new Runnable() {

			@Override
			public void run() {
				List<ContactModel> list = new ArrayList<ContactModel>();
				Cursor cursor = null;
				try {
					String[] PHONES_PROJECTION = new String[]{Phone.DISPLAY_NAME,
							Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID};

					ContentResolver resolver = mContext.getContentResolver();
					// 获取手机联系人
					cursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

					int PHONES_NUMBER_INDEX = cursor.getColumnIndex(Phone.NUMBER);
					int PHONES_DISPLAY_NAME_INDEX = cursor.getColumnIndex(Phone.DISPLAY_NAME);
					int PHONES_CONTACT_ID_INDEX = cursor.getColumnIndex(Phone.CONTACT_ID);
					int PHONES_PHOTO_ID_INDEX = cursor.getColumnIndex(Phone.PHOTO_ID);

					if (cursor != null) {
						while (cursor.moveToNext()) {

							// 得到手机号码
							String phoneNumber = cursor.getString(PHONES_NUMBER_INDEX);

							// 当手机号码为空的或者为空字段 跳过当前循环
							if (TextUtils.isEmpty(phoneNumber))
								continue;

							// 得到联系人名称
							String contactName = cursor.getString(PHONES_DISPLAY_NAME_INDEX);

							// 得到联系人ID
							Long contactid = cursor.getLong(PHONES_CONTACT_ID_INDEX);

							// 得到联系人头像ID
							Long photoid = cursor.getLong(PHONES_PHOTO_ID_INDEX);

							// 得到联系人头像Bitamp
							Bitmap contactPhoto = null;

							// photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
							if (photoid > 0) {
								Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
								InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
								contactPhoto = BitmapFactory.decodeStream(input);
							}

							ContactModel contact = new ContactModel();
							contact.setContactId(contactid);
							contact.setPhotoId(photoid);
							contact.setName(contactName);
							contact.setPhoto(contactPhoto);
							contact.setNumber(phoneNumber);

							// 添加到列表 
							list.add(contact);
						}
						refreshUI(list);
					}

				} catch (Exception e) {
					refreshUI(list);
					e.printStackTrace();

				} finally {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							dialog.dismiss();
						}
					});

					if (cursor != null) {
						cursor.close();
						cursor = null;
					}
				}
			}
		});

	}

	// 刷新UI
	private void refreshUI(final List<ContactModel> list) {

		contactLists = new ArrayList<SortModel>();

		if (list == null || list.size() == 0) {
			List<SortModel> sortList = filledData(getResources().getStringArray(R.array.contacts),
					getResources().getStringArray(R.array.img_src_contacts));
			contactLists.addAll(sortList);

		} else {
			contactLists.addAll(convertData(list));
		}

		// 根据a-z进行排序源数据
		Collections.sort(contactLists, pinyinComparator);

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				sourceDateList.clear();
				Head head = new Head();
				sourceDateList.add(head);
				sourceDateList.addAll(contactLists);
				footerView.setContactTotal(sourceDateList.size() - 1);
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	// 数据转化
	private List<SortModel> convertData(List<ContactModel> list) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < list.size(); i++) {
			ContactModel s = list.get(i);
			SortModel sortModel = new SortModel();
			sortModel.setContactModel(s);
			if (s.getPhoto() == null) {
				Random random = new Random();
				int ran = random.nextInt(41);
				String[] picContacts = getResources().getStringArray(R.array.img_src_contacts);
				sortModel.setImgSrc(picContacts[ran]);
			}

			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(s.getName());
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

	/**
	 * 为ListView填充数据
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
