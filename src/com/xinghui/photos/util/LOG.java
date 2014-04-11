package com.xinghui.photos.util;

import android.util.Log;


public class LOG {

	public static final boolean PRINT = true;
	
	public static void i(String tag,String msg){
		if(PRINT){
			Log.i(tag, msg);
		}
	}
	
	public static void i(Class<Object> tag,String msg){
		if(PRINT){
			Log.i(tag.getName(), msg);
		}
	}
	
}
