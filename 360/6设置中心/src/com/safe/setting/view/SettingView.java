package com.safe.setting.view;

import com.safe.setting.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * �Զ������ԣ�
 * ��attrs.xml���涨������
 * ����Ҫ������Щ�Զ������ԵĲ����ļ�����������Զ������Ե���activity_main.xml���������ռ�
 * ��Ϊһ�㶼�����Զ���ؼ�������Զ������ԣ�
 * �������Զ���ؼ���ĵڶ������캯���ó��Զ������Ե�value������activity.xml������Զ������Ե�value��
 * 
 * @author Administrator
 *
 */
public class SettingView extends RelativeLayout{

	private TextView tv_message;
	private CheckBox cb_message;
	private String title;
	private String desc_on;
	private String desc_off;
	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.safe.setting", "my_title");
		desc_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.safe.setting", "desc_on");
		desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.safe.setting", "desc_off");
		System.out.println("title =" + title);
		System.out.println("desc_on = " + desc_on);
		System.out.println("desc_off=" + desc_off);
	}

	public SettingView(Context context) {
		super(context);
		init();
	}

	public void setCheck(boolean flag) {
		if(flag == true) {
			tv_message.setText("�Ѿ�������");
		} else {
			tv_message.setText("��û�����ø���");
			
		}
		cb_message.setChecked(flag);
	}
	public boolean getCheck() {
		return cb_message.isChecked();
	}
	private void init() {
		//��3���������õ��� �������ļ����ɵ�View�ĸ���������Ѹ�View���ڸ�����
		//����ĸ������this����������Զ������
		View view = View.inflate(getContext(), R.layout.update_item, this);
		tv_message = (TextView) view.findViewById(R.id.tv_message);
		cb_message = (CheckBox) view.findViewById(R.id.cb_message);
		
	}

	
}
