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
	 * ���½����ֻ�����������ҳ��
	 * 
	 * @param view
	 */
	public void enterSetupOnclick(View view) {
		Intent intent = new Intent(this, SetUp1Activity.class);
		startActivity(intent);
		// �رյ�ǰҳ��
		finish();
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
}
