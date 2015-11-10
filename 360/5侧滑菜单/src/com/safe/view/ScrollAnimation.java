package com.safe.view;


import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * ��ָ��view��һ��ʱ����scrollTo��ָ��λ��
 * @author Administrator
 *
 */
public class ScrollAnimation extends Animation{
	
	private View view;
	private int targetScrollX;
	private int startScrollX;
	private int totalValue;
	
	public ScrollAnimation(View view, int targetScrollX) {
		super();
		this.view = view;
		this.targetScrollX = targetScrollX;
		
		startScrollX = view.getScrollX();
		totalValue = this.targetScrollX - startScrollX;
		
		int time = Math.abs(totalValue);
		setDuration(time);
	}



	/**
	 * ��ָ����ʱ����һֱִ�и÷�����ֱ����������
	 * interpolatedTime��0-1  ��ʶ����ִ�еĽ��Ȼ��߰ٷֱ�
	 * time :  0   - 0.5  - 0.7  -   1
	 * value:  10  -  60  -  80  -  110
	 * ��ǰ��ֵ = ��ʼֵ + �ܵĲ�ֵ*interpolatedTime
	 */
	@Override
	protected void applyTransformation(float interpolatedTime,
			Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		int currentScrollX = (int) (startScrollX + totalValue*interpolatedTime);
		view.scrollTo(currentScrollX, 0);
	}
}

