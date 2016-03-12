/** 
 * @Title:  ContactModel.java 
 * @author:  lee.sz
 * @data:  2016年3月12日 下午12:52:25 <创建时间>
 * 
 * @history：<以下是历史记录>
 *
 * @modifier: <修改人>
 * @modify date: 2016年3月12日 下午12:52:25 <修改时间>
 * @log: <修改内容>
 *
 * @modifier: <修改人>
 * @modify date: 2016年3月12日 下午12:52:25 <修改时间>
 * @log: <修改内容>
 */
package com.road.ui.addrbook;

import android.graphics.Bitmap;

import com.road.bean.SuperBean;

/** 
 * 联系人 
 * @author lee.sz 
 * @versionCode 1 <每次修改提交前+1>
 */
@SuppressWarnings("serial")
public class ContactModel extends SuperBean {
	
	private long contactId;     // 得到联系人ID
	private long photoId;		// 得到联系人头像ID
	private String name;		// 显示的名称
	private String number;		// 手机号码
	private Bitmap photo;		// 显示头像
	
	public long getContactId() {
		return contactId;
	}
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	public long getPhotoId() {
		return photoId;
	}
	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Bitmap getPhoto() {
		return photo;
	}
	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}
	
	
	
}
