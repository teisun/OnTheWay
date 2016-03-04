package com.road.sortlistview;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.road.ui.addrbook.ContactsHeadView;
import com.road.ui.addrbook.Head;
import com.squareup.picasso.Picasso;
import com.zhou.ontheway.R;

@SuppressLint({"DefaultLocale", "InflateParams"})
public class SortAdapter extends BaseAdapter implements SectionIndexer {

	private List<Object> list;
	private Context mContext;
	
	private static final int VIEW_TYPE = 2;
	private static final int TYPE_HEAD = 0;
	private static final int TYPE_CONTENT = 1;

	public SortAdapter(Context context, List<Object> list) {
		this.mContext = context;
		this.list = list;
	}
	
	/**
	 * 当 ListView 数据发生变化时,调用此方法来更新 ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<Object> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE;
	}
	
	@Override
	public int getItemViewType(int position) {
		Object object = list.get(position);
		if (object instanceof Head) {
			return TYPE_HEAD;
		} else {
			return TYPE_CONTENT;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HeadViewHolder headHolder = null;
		ContentViewHolder contentHolder = null;
		
		int viewType = getItemViewType(position);
		if (convertView == null) {
			switch (viewType) {
			case TYPE_HEAD:
				headHolder = new HeadViewHolder();
				convertView = new ContactsHeadView(mContext);
				headHolder.headView = (ContactsHeadView) convertView;
				convertView.setTag(headHolder);
				break;
			case TYPE_CONTENT:
				contentHolder = new ContentViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address_book_adapter, null);
				contentHolder.tvHead = (ImageView) convertView.findViewById(R.id.img_pic);
				contentHolder.tvTitle = (TextView) convertView.findViewById(R.id.txt_name);
				contentHolder.tvLetter = (TextView) convertView.findViewById(R.id.txt_index);
				convertView.setTag(contentHolder);
				break;
			}
		} else {
			switch (viewType) {
			case TYPE_HEAD:
				headHolder = (HeadViewHolder) convertView.getTag();
				break;
			case TYPE_CONTENT:
				contentHolder = (ContentViewHolder) convertView.getTag();
				break;
			}
		}
		
		SortModel item = null;
		Object obj = list.get(position);
		if (obj instanceof SortModel) {
			item = (SortModel) obj;
		}
		fillAdapterData(viewType, position, headHolder, contentHolder, item);
		
		return convertView;

	}
	
	private void fillAdapterData(int viewType, int position, HeadViewHolder headHolder, 
			ContentViewHolder contentHolder, SortModel item) {
		switch (viewType) {
		case TYPE_HEAD:
			headHolder.headView.setMessageCount(5);
			break;

		case TYPE_CONTENT:
			if (item == null) return;
			
			// 根据position获取分类的首字母的Char ascii值
			int section = getSectionForPosition(position);
			
			// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if (position == getPositionForSection(section)) {
				contentHolder.tvLetter.setVisibility(View.VISIBLE);
				contentHolder.tvLetter.setText(item.getSortLetters());
			} else {
				contentHolder.tvLetter.setVisibility(View.GONE);
			}
			
			if (!TextUtils.isEmpty(item.getImgSrc())) {
				Picasso.with(mContext).load(item.getImgSrc())
						.placeholder(R.drawable.defpic)
						.error(R.drawable.defpic).into(contentHolder.tvHead);
			} else {
				contentHolder.tvHead.setImageResource(R.drawable.defpic);
			}
			
			if (!TextUtils.isEmpty(item.getName())) {
				contentHolder.tvTitle.setText(item.getName());
			} else {
				contentHolder.tvTitle.setText("");
			}
			break;
		}
	}
	
	static class HeadViewHolder {
		ContactsHeadView headView;
	}
	
	static class ContentViewHolder {
		ImageView tvHead;
		TextView tvLetter;
		TextView tvTitle;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	@Override
	public int getSectionForPosition(int position) {
		Object object = list.get(position);
		if (object instanceof Head) {
			return -1;
		} else {
			return ((SortModel)object).getSortLetters().charAt(0);
		}
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@Override
	public int getPositionForSection(int section) {
		for (int i = 1; i < getCount(); i++) {
			String sortStr;
			Object object = list.get(i);
			if (object instanceof Head) {
				return -1;
			} else {
				sortStr = ((SortModel)object).getSortLetters();
			}
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public Object[] getSections() {
		return null;
	}
	
	
}