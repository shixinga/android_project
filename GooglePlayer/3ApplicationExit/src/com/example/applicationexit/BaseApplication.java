package com.example.applicationexit;

import android.app.Application;
import android.content.Context;
/**
 * 代表当前应用程序
 * Application.class 是Context的子类
 * @author itcast
 *该类用在UiUtils.java中，主要是获取该应用的resource
 *remember：在AndroidManifest.xml里面的application标签里加android:name="com.example.applicationexit.BaseApplication"
 */
public class BaseApplication extends Application {
	private static BaseApplication application;
	@Override
	public void onCreate() {
		super.onCreate();
		application=this;
		
	}
	public static Context getApplication() {
		return application;
	}
	
	
}
