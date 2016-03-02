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
import com.squareup.picasso.Picasso;
import com.zhou.ontheway.R;

@SuppressLint({"DefaultLocale", "InflateParams"})
public class SortAdapter extends BaseAdapter implements SectionIndexer {

	private List<SortModel> list;
	private Context mContext;

	public SortAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}

	/**
	 * 当 ListView 数据发生变化时,调用此方法来更新 ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<SortModel> list) {
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
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item_address_book_adapter, null);
			viewHolder.tvHead = (ImageView) view.findViewById(R.id.img_pic);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.txt_name);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.txt_index);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		SortModel item = list.get(position);
		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);

		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(item.getSortLetters());
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		
		if (!TextUtils.isEmpty(item.getImgSrc())) {
			Picasso.with(mContext).load(item.getImgSrc()).placeholder(R.drawable.defpic).error(R.drawable.defpic).into(viewHolder.tvHead); 
		} else {
			viewHolder.tvHead.setImageResource(R.drawable.defpic);
		}
		
		if (!TextUtils.isEmpty(item.getName())) {
			viewHolder.tvTitle.setText(item.getName());
		} else {
			viewHolder.tvTitle.setText("");
		}

		return view;

	}

	static class ViewHolder {
		ImageView tvHead;
		TextView tvLetter;
		TextView tvTitle;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	@Override
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
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