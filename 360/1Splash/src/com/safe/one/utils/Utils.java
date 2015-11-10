package com.safe.one.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

	public static String getStringByStream(InputStream is) {
		byte[] buffer = new byte[1024];
		int n;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			while((n = is.read(buffer)) != -1) {
				baos.write(buffer, 0, n);
			}
			return new String(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
