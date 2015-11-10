package com.example.mainactivity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
/**
 * ActionBarActivity是兼容4.0以下版本时用的，所以该activity
 * 在android.support.v7.app包中，而对于高于4.0版本的直接extends Activity
 * @author Administrator
 * 
 * LOGO按钮响应事件
 *
 */
public class SecondActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	}

	public void finishActivityOnClick(View view) {
		finish();
	}
}
