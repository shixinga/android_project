package com.example.applicationexit;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * 抽取BaseActivity 管理所有activity 方便退出
 * 
 * @author Administrator
 * 所有注释的代码是第二种杀死所有activity并退出的方法
 */
public class BaseActivity extends ActionBarActivity {
	// 管理运行的所有的activity
	public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

	// private KillAllReceiver receiver;
	// private class KillAllReceiver extends BroadcastReceiver{
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// finish();
	// }
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// receiver=new KillAllReceiver();
		// IntentFilter filter=new IntentFilter("com.itheima.google.killall");
		// registerReceiver(receiver, filter);

		synchronized (mActivities) {
			mActivities.add(this);
		}
		init();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		synchronized (mActivities) {
			mActivities.remove(this);
System.out.println("aaaaaaaaa");
		}
		
		//在MainActivity和SecondActivity中的Button中sendBroadcast就行了
		// if(receiver!=null){
		// unregisterReceiver(receiver);
		// receiver=null;
		// }
	}

	//杀死所有activity并退出的第一种方法
	public void killAll() {
		// 复制了一份mActivities 集合
		List<BaseActivity> copy;
		synchronized (mActivities) {
			copy = new LinkedList<BaseActivity>(mActivities);
		}
		for (BaseActivity activity : copy) {
			activity.finish();
		}
		
		//执行到这里时activity并没有被destroy掉
System.out.println(mActivities.size());
		
		// 杀死当前的进程
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	protected void init() {
	}
}
