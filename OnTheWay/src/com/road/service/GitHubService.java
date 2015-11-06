package com.road.service;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

import com.road.bean.Res;


public interface GitHubService {
	
	@FormUrlEncoded
	@POST("joke_text")
	Call<Res> interfaceName(@Header("apikey") String key, @Field("page") String page);
	
}
