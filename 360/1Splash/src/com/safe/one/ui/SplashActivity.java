package com.safe.one.ui;

import java.io.File;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.safe.one.utils.Utils;

public class SplashActivity extends Activity {

	private TextView tv_version;
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if(msg.what == UPDATE) {
				Toast.makeText(SplashActivity.this, "version   needs to be update", 0).show();
				showUpdateDialog();
			} else if(msg.what == NOT_UPDATE) {
				Toast.makeText(SplashActivity.this, "version does not need to be update", 0).show();
				loadMainActivity();
			} else if(msg.what == ACCESS_NETWORK_FAILED) {
				Toast.makeText(SplashActivity.this, "access network failed", 0).show();
				loadMainActivity();
			}
		}

		
	};

	protected String mVersionName;

	protected int mVersionCode;

	protected String mDesc;

	protected String mDownloadUrl;

	
	protected static final int ACCESS_NETWORK_FAILED = 0;
	protected static final int UPDATE = 1;
	
	protected static final int NOT_UPDATE = 2;
	private static final String PATH = "http://192.168.1.102:8080/update.json";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// ȡ��������,����������λ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// ��ɴ����ȫ����ʾ // ȡ����״̬��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		tv_version = (TextView) findViewById(R.id.tv_version);
		tv_version.setText(getLocalAppVersionName());
		
		checkUpdate();
	}

	protected void showUpdateDialog() {
		// �Ի���Ĵ�����
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("�����Ի������");
		builder.setMessage("����Ҫ������");
		builder.setPositiveButton("����", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(getApplicationContext(), "ȷ���������", 0).show();
				downloadApp();
			}
		});
		builder.setNegativeButton("�´���˵", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ʲô����дĬ��ʵ�־��ǹرյ��Ի���
//				Toast.makeText(getApplicationContext(), "ȡ���������", 0).show();
				loadMainActivity();
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}

	protected void downloadApp() {
		//��sdcard�ܲ�����
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			final ProgressDialog pd;
			pd = new ProgressDialog(SplashActivity.this);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setMessage("��������...");
			pd.show();
			//�����·��
			String target = Environment.getExternalStorageDirectory()
					+ "/update.apk";
			HttpUtils http = new HttpUtils();
			HttpHandler handler = http.download(mDownloadUrl,
					target,//�洢�ļ�·��
			    true, // ���Ŀ���ļ����ڣ�����δ��ɵĲ��ּ������ء���������֧��RANGEʱ���������ء�
			    true, // ��������󷵻���Ϣ�л�ȡ���ļ�����������ɺ��Զ���������
			    new RequestCallBack<File>() {


			        @Override
			        public void onLoading(long total, long current, boolean isUploading) {
//			            tv_percent.setText(current + "/" + total);
			        	pd.setMax((int)total);
			        	pd.setProgress((int)current);
			        	pd.setMessage(current * 100 / total + "%");
			            
			        }

			        @Override
			        public void onSuccess(ResponseInfo<File> responseInfo) {
			        	pd.setMessage("downloaded:" + responseInfo.result.getPath());
			        	pd.dismiss();
			        	// ��ת��ϵͳ����ҳ��
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.addCategory(Intent.CATEGORY_DEFAULT);
						intent.setDataAndType(Uri.fromFile(responseInfo.result),
								"application/vnd.android.package-archive");
						// startActivity(intent);
						startActivityForResult(intent, 0);// ����û�ȡ����װ�Ļ�,
															// �᷵�ؽ��,�ص�����onActivityResult
			        }


			        @Override
			        public void onFailure(HttpException error, String msg) {
			        	pd.dismiss();
			        	Toast.makeText(SplashActivity.this, "����ʧ��!",
								Toast.LENGTH_SHORT).show();
			        	loadMainActivity();
			        }
			});

		} else {
			Toast.makeText(SplashActivity.this, "�ڴ濨������", 0).show();
		}
		
	}
	// ����û�ȡ����װ�Ļ�,�ص��˷���
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			loadMainActivity();
			super.onActivityResult(requestCode, resultCode, data);
		}
	private void checkUpdate() {
		new Thread() {


			@Override
			public void run() {
				final long startTime = System.currentTimeMillis();
				Message msg = handler.obtainMessage();
    	    	//ʹ��httpClient�����get��ʽ�ύ
    	    	//1.����HttpClient����
    	    	HttpClient hc = new DefaultHttpClient();
    	    	
    	    	//2.����httpGet���󣬹��췽���Ĳ���������ַ
    	    	HttpGet hg = new HttpGet(PATH);
    	    	
    	    	//3.ʹ�ÿͻ��˶��󣬰�get��������ͳ�ȥ
    				HttpResponse hr;
					try {
						hr = hc.execute(hg);
						//�õ���Ӧͷ�е�״̬��
						StatusLine sl = hr.getStatusLine();
						if(sl.getStatusCode() == 200){
							//�õ���Ӧͷ��ʵ��
							HttpEntity he = hr.getEntity();
							//�õ�ʵ���е����ݣ���ʵ���Ƿ��������ص�������
							InputStream is = he.getContent();
							String text = Utils.getStringByStream(is);
							JSONObject jo = new JSONObject(text);
							mVersionName = jo.getString("versionName");
							mVersionCode = jo.getInt("versionCode");
							mDesc = jo.getString("description");
							mDownloadUrl = jo.getString("downloadUrl");
							
							if(mVersionCode > getLocalAppVersionCode()) {
								msg.what = UPDATE;
							} else {
								msg.what = NOT_UPDATE;
							}
						}
					} catch (Exception e) {
						msg.what = ACCESS_NETWORK_FAILED;
						e.printStackTrace();
					} finally {
						long endTime = System.currentTimeMillis();
						long timeUsed = endTime - startTime;// �������绨�ѵ�ʱ��
						if (timeUsed < 2000) {
							// ǿ������һ��ʱ��,��֤����ҳչʾ2����
							try {
								Thread.sleep(2000 - timeUsed);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						handler.sendMessage(msg);
					}
			}
			
		}.start();
	}

	private void loadMainActivity() {
		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		startActivity(intent);
		finish();// �ѵ�ǰactivity������ջ�����Ƴ�
	}
	
	//get the local versionName
	public String getLocalAppVersionName() {
		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			String versionName = packageInfo.versionName;
			int versionCode = packageInfo.versionCode;
System.out.println("versionName = " + versionName + ",versionCode = " + versionCode);
			return versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return " get versionName failed";
	}
	//get the local versionCode
	public int getLocalAppVersionCode() {
		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			String versionName = packageInfo.versionName;
			int versionCode = packageInfo.versionCode;
System.out.println("versionName = " + versionName + ",versionCode = " + versionCode);
			return versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
