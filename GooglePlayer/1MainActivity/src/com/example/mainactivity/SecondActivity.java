package com.example.mainactivity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
/**
 * ActionBarActivity�Ǽ���4.0���°汾ʱ�õģ����Ը�activity
 * ��android.support.v7.app���У������ڸ���4.0�汾��ֱ��extends Activity
 * @author Administrator
 * 
 * LOGO��ť��Ӧ�¼�
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
