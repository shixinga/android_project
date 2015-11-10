package com.safe.direct;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * 重新进入手机防盗设置向导页面
	 * 
	 * @param view
	 */
	public void enterSetupOnclick(View view) {
		Intent intent = new Intent(this, SetUp1Activity.class);
		startActivity(intent);
		// 关闭当前页面
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
}
