package com.example.applicationexit;

import android.app.Application;
import android.content.Context;
/**
 * ������ǰӦ�ó���
 * Application.class ��Context������
 * @author itcast
 *��������UiUtils.java�У���Ҫ�ǻ�ȡ��Ӧ�õ�resource
 *remember����AndroidManifest.xml�����application��ǩ���android:name="com.example.applicationexit.BaseApplication"
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