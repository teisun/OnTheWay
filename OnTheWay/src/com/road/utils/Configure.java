package com.road.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;

public class Configure {

	public static int screenHeight = 0;
	public static int screenWidth = 0;
	public static float screenDensity = 0;
	public static int densityDpi = 0;
	public static int version = Integer.valueOf(android.os.Build.VERSION.SDK_INT);

	public static void init(Activity context) {
		if (screenDensity == 0 || screenWidth == 0 || screenHeight == 0) {
			DisplayMetrics dm = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(dm);
			Configure.screenDensity = dm.density;
			Configure.screenHeight = dm.heightPixels;
			Configure.screenWidth = dm.widthPixels;
			Configure.densityDpi = dm.densityDpi;
		}
		Log.i("SCREEN CONFIGURE", "screenHeight:" + screenHeight
				+ ";screenWidth:" + screenWidth + ";screenDensity:"
				+ screenDensity + ";densityDpi:" + densityDpi);
	}

}