package com.safe.flush;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.safe.flush.MyFlushListView.OnRefreshListener;

public class MainActivity extends Activity {

	private MyFlushListView flv_show;
	private List<String> list = new ArrayList<>();
	private MyAdapter myAdapter;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//����UI
			myAdapter.notifyDataSetChanged();
			flv_show.completeRefresh();
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initData() {
		for(int i = 0; i < 20; ++i) {
			list.add("���ݣ���" + i);
		}
	}

	private void initView() {

		flv_show = (MyFlushListView) findViewById(R.id.flv_show);
		
		/*
		 * addHeaderView�����ַ����������аѵڶ��ַ���ж���Զ������MyFlushListView.class��
		 * final View headerView = View.inflate(this, R.layout.layout_header, null);
		//��һ�ַ���
		headerView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int headerViewHeight = headerView.getHeight();
				
				
				Log.e("MainActivity", "headerViewHeight: "+headerViewHeight);
				headerView.setPadding(0, -headerViewHeight, 0, 0);
				refreshListView.addHeaderView(headerView);//
			}
		});
		//�ڶ��ַ���
		headerView.measure(0, 0);//����֪ͨϵͳȥ����
		int headerViewHeight = headerView.getMeasuredHeight();
		Log.e("MainActivity", "headerViewHeight: "+headerViewHeight);
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		refreshListView.addHeaderView(headerView);//
*/		
		
		
		myAdapter = new MyAdapter();
		flv_show.setAdapter(myAdapter);
		
		flv_show.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onPullRefresh() {
				//��Ҫ������������������ݣ�Ȼ�����UI
				requestDataFromServer(false);
			}

			@Override
			public void onLoadingMore() {
				requestDataFromServer(true);
			}
		});
		
	}

	/**
	 * ģ�����������������
	 */
	private void requestDataFromServer(final boolean isLoadingMore){
		new Thread(){
			public void run() {
				SystemClock.sleep(3000);//ģ�������������һ��ʱ�䳤��
				
				if(isLoadingMore){
					list.add("���ظ��������-1");
					list.add("���ظ��������-2");
					list.add("���ظ��������-3");
				}else {
					list.add(0, "����ˢ�µ�����");
				}
				
				//��UI�̸߳���UI
				handler.sendEmptyMessage(0);
			};
		}.start();
	}
	
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view = new TextView(MainActivity.this);
			view.setText(list.get(position));
			return view;
		}
		
	}
}
