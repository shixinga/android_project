package com.example.applicationexit;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends BaseActivity {

	
	@Override
	protected void init() {
		setContentView(R.layout.activity_main);
	}


	public void secondActivityOnClick(View view) {
		Intent intent = new Intent(this, SecondActivity.class);
		startActivity(intent);
	}
	public void exitOnClick(View view) {
		//ɱ������activity���˳��ĵڶ��ַ���
		//sendBroadcast(new Intent("com.itheima.google.killall"));
		
		//ɱ������activity���˳��ĵ�һ�ַ���
		killAll();
	}
}
