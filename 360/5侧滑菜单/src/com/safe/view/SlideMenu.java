package com.safe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SlideMenu extends FrameLayout{
	private View menuView,mainView;
	private int menuWidth = 0;
	private Scroller scroller;
	public SlideMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SlideMenu(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		scroller = new Scroller(getContext());
	}
	
	/**
	 * ��1������viewȫ����������ã������ó�ʼ����view������
	 * ע�⣬�����޷���ȡ��view�Ŀ��
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		menuView = getChildAt(0);
		mainView = getChildAt(1);
		menuWidth = menuView.getLayoutParams().width;
	}
	
	/**
	 * widthMeasureSpec��heightMeasureSpec��ϵͳ����SlideMenuʱ����Ĳ�����
	 * ��2�������������Ŀ������SlideMenu�������壬��ʵ�����õ�����Ļ���
	 */
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		
//		int measureSpec = MeasureSpec.makeMeasureSpec(menuWidth, MeasureSpec.EXACTLY);
//		
//		//����������view�Ŀ��
//		//ͨ��getLayoutParams�������Ի�ȡ�������ļ���ָ�����
//		menuView.measure(measureSpec, heightMeasureSpec);
//		//ֱ��ʹ��SlideMenu�Ĳ�����������Ϊ���Ŀ�߶��ǳ���������
//		mainView.measure(widthMeasureSpec, heightMeasureSpec);
//		
//	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = (int) ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaX = (int) ( ev.getX()- downX);
			
			if(Math.abs(deltaX)>8){
				return true;
			}
			break;
		}
		return super.onInterceptTouchEvent(ev);
//		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * l: ��ǰ��view������ڸ�view������ϵ�е�x����
	 * t: ��ǰ��view�Ķ����ڸ�view������ϵ�е�y����
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		Log.e("MAIN", "L: "+l+"   t: "+t  +"  r: "+r  + "   b: "+b);
		menuView.layout(-menuWidth, 0, 0, menuView.getMeasuredHeight());
		mainView.layout(0, 0, r, b);
	}
	
	private int downX;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getX();
			int deltaX = (int) ( moveX- downX);
			
			int newScrollX = getScrollX() - deltaX;
			
			if(newScrollX<-menuWidth)newScrollX = -menuWidth;
			if(newScrollX>0)newScrollX = 0;
			
			Log.e("Main", "scrollX: "+getScrollX());
			scrollTo(newScrollX, 0);
			downX = moveX;
			break;
		case MotionEvent.ACTION_UP:
			//1.ʹ���Զ��嶯��
//			ScrollAnimation scrollAnimation;
//			if(getScrollX()>-menuWidth/2){
//				//�رղ˵�
////				scrollTo(0, 0);
//				scrollAnimation = new ScrollAnimation(this, 0);
//			}else {
//				//�򿪲˵�
////				scrollTo(-menuWidth, 0);
//				scrollAnimation = new ScrollAnimation(this, -menuWidth);
//			}
//			startAnimation(scrollAnimation);
			//2.ʹ��Scroller
			if(getScrollX()>-menuWidth/2){
//				//�رղ˵�
				closeMenu();
			}else {
				//�򿪲˵�
				openMenu();
			}
			break;
		}
		return true;
	}
	
	private void closeMenu(){
		scroller.startScroll(getScrollX(), 0, 0-getScrollX(), 0, 400);
		invalidate();
	}
	
	private void openMenu(){
		scroller.startScroll(getScrollX(), 0, -menuWidth-getScrollX(), 0, 400);
		invalidate();
	}
	
	/**
	 * Scroller������ȥ�����������
	 * ��invalidate()���Ե��������
	 * invalidate->draw->computeScroll
	 */
	@Override
	public void computeScroll() {
		super.computeScroll();
		if(scroller.computeScrollOffset()){//����true,��ʾ����û����
			scrollTo(scroller.getCurrX(), 0);
			invalidate();
		}
	}

	/**
	 * �л��˵��Ŀ��͹�
	 */
	public void switchMenu() {
		if(getScrollX()==0){
			//��Ҫ��
			openMenu();
		}else {
			//��Ҫ�ر�
			closeMenu();
		}
	}
	
	

}
