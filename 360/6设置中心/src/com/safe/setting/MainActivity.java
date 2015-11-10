package com.safe.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.safe.setting.view.SettingView;

public class MainActivity extends Activity {

	private SettingView sv_show;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sv_show = (SettingView) findViewById(R.id.sv_show);
		sv_show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(sv_show.getCheck()) {
					sv_show.setCheck(false);
				} else {
					sv_show.setCheck(true);
				}
			}
		});
	}

}
