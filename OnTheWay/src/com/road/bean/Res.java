package com.road.bean;

import java.io.Serializable;

public class Res implements Serializable{
	
	public int showapi_res_code;
	
	public String showapi_res_error;
	
	public BeanBody showapi_res_body;

	@Override
	public String toString() {
		return "Res [showapi_res_code=" + showapi_res_code
				+ ", showapi_res_error=" + showapi_res_error
				+ ", showapi_res_body=" + showapi_res_body + "]";
	}
	
	
	
}
