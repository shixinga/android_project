package com.safe.address;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	public static final String ADDRESS_DB_NAME = "address.db";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		copyDB(ADDRESS_DB_NAME);
	}

	private void copyDB(String dbName) {
		// File filesDir = getFilesDir();
		// System.out.println("路径:" + filesDir.getAbsolutePath());
		File destFile = new File(getFilesDir(), dbName);// 要拷贝的目标地址

		if (destFile.exists()) {
			System.out.println("数据库" + dbName + "已存在!");
			return;
		}

		FileOutputStream out = null;
		InputStream in = null;

		try {
			in = getAssets().open(dbName);
			out = new FileOutputStream(destFile);

			int len = 0;
			byte[] buffer = new byte[1024];

			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 归属地查询
	 * 
	 * @param view
	 */
	public void numberAddressQueryOnClick(View view) {
		startActivity(new Intent(this, AddressActivity.class));
	}

}
