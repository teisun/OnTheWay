package com.road.sortlistview;

import android.graphics.Bitmap;

import com.road.ui.addrbook.ContactModel;

/**
 * 联系人相关信息
 * 一个是 ListView 的 name,另一个就是显示的 name 拼音的首字母
 * @author zhou.sz 
 * @versionCode 1 <每次修改提交前+1>
 */
public class SortModel {
	
	private String name;		// 显示的名称
	private String number;		// 手机号码
	private Bitmap photo;		// 显示头像
	private String imgSrc; 		// 显示头像 (备用)
	private String sortLetters;	// 显示数据拼音的首字母  
	
	public void setContactModel(ContactModel model) {
		this.name = model.getName();
		this.number = model.getNumber();
		this.photo = model.getPhoto();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSortLetters() {
		return sortLetters;
	}
	
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	
	public String getImgSrc() {
		return imgSrc;
	}
	
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	
}
