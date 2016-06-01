package com.road.bean;

public class Info<T> extends SuperBean{
	
	private T data;
	private Class origin;  
	
	public Info() {
		super();
	}

	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public Class getOrigin() {
		return origin;
	}
	
	public void setOrigin(Class origin) {
		this.origin = origin;
	}


}
