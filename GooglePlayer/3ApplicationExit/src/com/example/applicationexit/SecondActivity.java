package com.example.applicationexit;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends BaseActivity {

	@Override
	protected void init() {
		setContentView(R.layout.activity_second);
		//以下是UiUtils的用法
		String array [] = UiUtils.getStringArray(R.array.motherFuckerArray);
		Toast.makeText(this, array[1] , 0).show();
	}

	public void exitOnClick(View view) {
		killAll();
	}
}
