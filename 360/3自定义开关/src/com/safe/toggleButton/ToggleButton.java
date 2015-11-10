package com.safe.toggleButton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ToggleButton extends View {

	private Bitmap bt_switch_bg;
	private Bitmap bt_slide_bg;
	public enum ToggleState {
		OPEN,CLOSE;
	}
	private ToggleState mState;
	
	private int mCurrentX = 0;
	private boolean mIsSliding;
	
	//�����ļ�����styleʱ����
	public ToggleButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	//�����ļ�����attributeʱ����
	public ToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//�ڴ����е���
	public ToggleButton(Context context) {
		super(context);
	}

	public void setSwitchBackgroundSrc() {
		bt_switch_bg = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
	}

	public void setSlideBackgroundSrc() {
		bt_slide_bg = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button_background);
	}

	public void setState(ToggleState state) {
		this.mState = state;
	}
	
	//���������֣�1.��Ļ���ꣻ2.�������
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		mCurrentX = (int) event.getX();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mIsSliding = true;
			break;
		case MotionEvent.ACTION_UP:
			mIsSliding = false;
			
			int centerX = bt_switch_bg.getWidth()/2;
			if(mCurrentX>centerX){
				//open
				if(mState!=ToggleState.OPEN){
					mState = ToggleState.OPEN;
					if(listener!=null){
						listener.onToggleStateChange(mState);
					}
				}
			}else {
				//close
				if(mState!=ToggleState.CLOSE){
					mState = ToggleState.CLOSE;
					if(listener!=null){
						listener.onToggleStateChange(mState);
					}
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			
			break;

		default:
			break;
		}
		
		//�����������ָToggleButton���ػ�
		invalidate();
		
		return true;
	}
	
	

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//1.���Ʊ���ͼƬ
		//left: ͼƬ����ߵ�x����
		//top: ͼƬ������y����
		canvas.drawBitmap(bt_switch_bg, 0, 0, null);
		//2.���ƻ������ͼƬ
		if(mIsSliding){
			int left = mCurrentX - bt_slide_bg.getWidth()/2;
			if(left<0)left = 0;
			if(left>(bt_switch_bg.getWidth() - bt_slide_bg.getWidth())){
				left = bt_switch_bg.getWidth() - bt_slide_bg.getWidth();
			}
			canvas.drawBitmap(bt_slide_bg, left, 0, null);
		}else {
			//��ʱ̧�𣬸���stateȥ���ƻ������λ��
			if(mState==ToggleState.OPEN){
				canvas.drawBitmap(bt_slide_bg, bt_switch_bg.getWidth()-bt_slide_bg.getWidth(), 0, null);
			}else {
				canvas.drawBitmap(bt_slide_bg, 0, 0, null);
			}
		}
	}
	
	/**
	 * ���õ�ǰ�ؼ���ʾ����Ļ�ϵĿ��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(bt_switch_bg.getWidth(), bt_switch_bg.getHeight());
	}

	private OnToggleStateListener listener;
	public interface OnToggleStateListener {
		void onToggleStateChange(ToggleState toggleState);
	}
	
	public void setOnToggleStateListener(OnToggleStateListener listener) {
		this.listener = listener;
	}
	
}
