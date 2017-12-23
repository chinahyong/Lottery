package com.pkx.lottery.bean;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyHomeViewPaper extends ViewPager {

	public MyHomeViewPaper(Context context) {
		super(context);
	}

	public MyHomeViewPaper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		requestDisallowInterceptTouchEvent(false);

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}
}
