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
		// 取消标题栏,必须放在这个位置
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// 完成窗体的全屏显示 // 取消掉状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		tv_version = (TextView) findViewById(R.id.tv_version);
		tv_version.setText(getLocalAppVersionName());
		
		checkUpdate();
	}

	protected void showUpdateDialog() {
		// 对话框的创建器
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("升级对话框标题");
		builder.setMessage("你想要升级吗");
		builder.setPositiveButton("升级", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(getApplicationContext(), "确定被点击了", 0).show();
				downloadApp();
			}
		});
		builder.setNegativeButton("下次再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 什么都不写默认实现就是关闭掉对话框
//				Toast.makeText(getApplicationContext(), "取消被点击了", 0).show();
				loadMainActivity();
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}

	protected void downloadApp() {
		//看sdcard能不能用
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			final ProgressDialog pd;
			pd = new ProgressDialog(SplashActivity.this);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setMessage("正在下载...");
			pd.show();
			//保存的路劲
			String target = Environment.getExternalStorageDirectory()
					+ "/update.apk";
			HttpUtils http = new HttpUtils();
			HttpHandler handler = http.download(mDownloadUrl,
					target,//存储文件路劲
			    true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
			    true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
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
			        	// 跳转到系统下载页面
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.addCategory(Intent.CATEGORY_DEFAULT);
						intent.setDataAndType(Uri.fromFile(responseInfo.result),
								"application/vnd.android.package-archive");
						// startActivity(intent);
						startActivityForResult(intent, 0);// 如果用户取消安装的话,
															// 会返回结果,回调方法onActivityResult
			        }


			        @Override
			        public void onFailure(HttpException error, String msg) {
			        	pd.dismiss();
			        	Toast.makeText(SplashActivity.this, "下载失败!",
								Toast.LENGTH_SHORT).show();
			        	loadMainActivity();
			        }
			});

		} else {
			Toast.makeText(SplashActivity.this, "内存卡不能用", 0).show();
		}
		
	}
	// 如果用户取消安装的话,回调此方法
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
    	    	//使用httpClient框架做get方式提交
    	    	//1.创建HttpClient对象
    	    	HttpClient hc = new DefaultHttpClient();
    	    	
    	    	//2.创建httpGet对象，构造方法的参数就是网址
    	    	HttpGet hg = new HttpGet(PATH);
    	    	
    	    	//3.使用客户端对象，把get请求对象发送出去
    				HttpResponse hr;
					try {
						hr = hc.execute(hg);
						//拿到响应头中的状态行
						StatusLine sl = hr.getStatusLine();
						if(sl.getStatusCode() == 200){
							//拿到响应头的实体
							HttpEntity he = hr.getEntity();
							//拿到实体中的内容，其实就是服务器返回的输入流
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
						long timeUsed = endTime - startTime;// 访问网络花费的时间
						if (timeUsed < 2000) {
							// 强制休眠一段时间,保证闪屏页展示2秒钟
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
		finish();// 把当前activity从任务栈里面移除
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
