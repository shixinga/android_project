package com.safe.toggleButton;

import com.safe.toggleButton.ToggleButton.OnToggleStateListener;
import com.safe.toggleButton.ToggleButton.ToggleState;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ToggleButton tb_zidingyi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tb_zidingyi = (ToggleButton) findViewById(R.id.tb_zidingyi);
		tb_zidingyi.setSwitchBackgroundSrc();
		tb_zidingyi.setSlideBackgroundSrc();
		tb_zidingyi.setState(ToggleState.OPEN);
		tb_zidingyi.setOnToggleStateListener(new OnToggleStateListener() {
			
			@Override
			public void onToggleStateChange(ToggleState toggleState) {
				Toast.makeText(MainActivity.this,
						"好强悍的观察者模式！！：" + (toggleState==ToggleState.OPEN?"开启":"关闭"),
						0).show();
			}
		});
	}
}
