package com.safe.address;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * �����ز�ѯҳ��
 * 
 * @author Kevin
 * 
 */
public class AddressActivity extends Activity {

	private EditText etNumber;
	private TextView tvResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_address);
		etNumber = (EditText) findViewById(R.id.et_number);
		tvResult = (TextView) findViewById(R.id.tv_result);

		// ����EditText�ı仯
		etNumber.addTextChangedListener(new TextWatcher() {

			// ���� �����仯ʱ�Ļص�
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String address = AddressDao.getAddress(s.toString());
				tvResult.setText(address);
			}

			// ���ֱ仯ǰ�Ļص�
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			// ���ֱ仯����֮��Ļص�
			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	/**
	 * ��ʼ��ѯ
	 * 
	 * @param view
	 */
	public void query(View view) {
		String number = etNumber.getText().toString().trim();

		if (!TextUtils.isEmpty(number)) {
			String address = AddressDao.getAddress(number);
			tvResult.setText(address);
		} else {
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);

			// shake.setInterpolator(new Interpolator() {
			//
			// @Override
			// public float getInterpolation(float x) {
			// //y=ax+b
			// int y=0;
			// return y;
			// }
			// });

			etNumber.startAnimation(shake);
			vibrate();
		}
	}

	/**
	 * �ֻ���, ��ҪȨ�� android.permission.VIBRATE
	 */
	private void vibrate() {
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		// vibrator.vibrate(2000);������
		vibrator.vibrate(new long[] { 1000, 2000, 1000, 3000 }, -1);// �ȵȴ�1��,����2��,�ٵȴ�1��,����3��,
																	// ��2����-1��ʾִֻ��һ��,��ѭ��,
																	// ��2����0��ʾ��ͷѭ��,
																	// ��2��ʾ�ӵڼ���λ�ÿ�ʼѭ��
		// ȡ����vibrator.cancel()
	}
}
