package com.safe.setting.view;

import com.safe.setting.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 自定义属性：
 * 在attrs.xml里面定义属性
 * 在需要引用这些自定义属性的布局文件里（这里引用自定义属性的是activity_main.xml）加命名空间
 * 因为一般都是在自定义控件里面加自定义属性，
 * 所以在自定义控件里的第二个构造函数得出自定义属性的value（即在activity.xml里面的自定义属性的value）
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
			tv_message.setText("已经更新了");
		} else {
			tv_message.setText("还没有设置更新");
			
		}
		cb_message.setChecked(flag);
	}
	public boolean getCheck() {
		return cb_message.isChecked();
	}
	private void init() {
		//第3个参数设置的是 被布局文件填充成的View的父组件（即把该View放在父组件里）
		//这里的父组件是this，即是这个自定义组件
		View view = View.inflate(getContext(), R.layout.update_item, this);
		tv_message = (TextView) view.findViewById(R.id.tv_message);
		cb_message = (CheckBox) view.findViewById(R.id.cb_message);
		
	}

	
}
