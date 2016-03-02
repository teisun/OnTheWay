package com.road.sortlistview;

/**
 * 一个是 ListView 的 name
 * 另一个就是显示的 name 拼音的首字母
 * @author zhou.sz 
 * @versionCode 1 <每次修改提交前+1>
 */
public class SortModel {
	
	private String name;		// 显示的数据  
	private String imgSrc; 		//显示头像
	private String sortLetters;	// 显示数据拼音的首字母  
	
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
	
}
