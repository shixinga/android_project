package com.example.applicationexit;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * ��ȡBaseActivity ��������activity �����˳�
 * 
 * @author Administrator
 * ����ע�͵Ĵ����ǵڶ���ɱ������activity���˳��ķ���
 */
public class BaseActivity extends ActionBarActivity {
	// �������е����е�activity
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
		
		//��MainActivity��SecondActivity�е�Button��sendBroadcast������
		// if(receiver!=null){
		// unregisterReceiver(receiver);
		// receiver=null;
		// }
	}

	//ɱ������activity���˳��ĵ�һ�ַ���
	public void killAll() {
		// ������һ��mActivities ����
		List<BaseActivity> copy;
		synchronized (mActivities) {
			copy = new LinkedList<BaseActivity>(mActivities);
		}
		for (BaseActivity activity : copy) {
			activity.finish();
		}
		
		//ִ�е�����ʱactivity��û�б�destroy��
System.out.println(mActivities.size());
		
		// ɱ����ǰ�Ľ���
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	protected void init() {
	}
}